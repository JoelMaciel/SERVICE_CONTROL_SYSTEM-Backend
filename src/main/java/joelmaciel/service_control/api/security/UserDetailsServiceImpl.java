package joelmaciel.service_control.api.security;

import joelmaciel.service_control.domain.model.Client;
import joelmaciel.service_control.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Client Not Found with email: " + username));
        return UserDetailsImpl.toUserDetailsImpl(client);
    }

    public UserDetails loadUserById(Long clientId) throws AuthenticationCredentialsNotFoundException {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Client Not Found with userId: " + clientId));
        return UserDetailsImpl.toUserDetailsImpl(client);
    }
}
