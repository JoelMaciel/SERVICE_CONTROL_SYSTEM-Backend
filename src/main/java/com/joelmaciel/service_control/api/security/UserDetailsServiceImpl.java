package com.joelmaciel.service_control.api.security;

import com.joelmaciel.service_control.domain.model.User;
import com.joelmaciel.service_control.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + username));
        return UserDetailsImpl.toUserDetailsImpl(user);
    }

    public UserDetails loadUserById(Long userId) throws AuthenticationCredentialsNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("User Not Found with userId: " + userId));
        return UserDetailsImpl.toUserDetailsImpl(user);
    }
}
