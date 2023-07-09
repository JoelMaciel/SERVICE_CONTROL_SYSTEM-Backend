package joelmaciel.service_control.domain.service;

import joelmaciel.service_control.api.dto.ClientDTO;
import joelmaciel.service_control.api.dto.request.ClientRequestDTO;
import joelmaciel.service_control.api.dto.request.ClientRequestUpdateDTO;
import joelmaciel.service_control.api.dto.validator.ClientValidator;
import joelmaciel.service_control.api.security.JwtProvider;
import joelmaciel.service_control.api.security.dto.JwtDTO;
import joelmaciel.service_control.api.security.dto.LoginDTO;
import joelmaciel.service_control.domain.enums.RoleType;
import joelmaciel.service_control.domain.exception.ClientNotFoundException;
import joelmaciel.service_control.domain.exception.InvalidLoginDataException;
import joelmaciel.service_control.domain.model.Client;
import joelmaciel.service_control.domain.model.Roles;
import joelmaciel.service_control.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RegistrationClientService {

    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;
    private final ClientRepository clientRepository;
    private final ClientValidator clientValidator;

    public List<ClientDTO> findAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream().map(client -> ClientDTO.toDTO(client))
                .collect(Collectors.toList());
    }

    public ClientDTO findById(Long clientId) {
        Client client = searchById(clientId);
        return ClientDTO.toDTO(client);
    }

    @Transactional
    public ClientDTO updateClient(Long clientId, @RequestBody ClientRequestUpdateDTO clientRequestDTO) {
        Client currentClient = searchById(clientId).toBuilder()
                .username(clientRequestDTO.getUsername())
                .email(clientRequestDTO.getEmail())
                .build();

        return ClientDTO.toDTO(clientRepository.save(currentClient));
    }

    @Transactional
    public ClientDTO saveClient(ClientRequestDTO clientRequestDTO) {
        clientValidator.validateExistingCpf(clientRequestDTO);
        clientValidator.validateEmail(clientRequestDTO);
        clientValidator.validateUsername(clientRequestDTO);

        Roles role = roleService.findByRoleName(RoleType.ROLE_USER);

        Client client = ClientRequestDTO.toModel(clientRequestDTO).toBuilder()
                .password(passwordEncoder.encode(clientRequestDTO.getPassword()))
                .roles(Collections.singleton(role))
                .build();

        return ClientDTO.toDTO(clientRepository.save(client));
    }

    @Transactional
    public void removeClient(Long clientId) {
        searchById(clientId);
        clientRepository.deleteById(clientId);
    }

    public JwtDTO authenticationUserLogin(LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new JwtDTO(jwtProvider.generateJwt(authentication));
        } catch (RuntimeException e) {
            throw new InvalidLoginDataException("Incorrect email or password.");
        }

    }

    public Client searchById(Long clientId) {
        return clientRepository.findById(clientId).orElseThrow(
                () -> new ClientNotFoundException(clientId));
    }
}
