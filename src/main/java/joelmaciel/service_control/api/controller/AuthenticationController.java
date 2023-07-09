package joelmaciel.service_control.api.controller;

import joelmaciel.service_control.api.controller.openapi.AuthenticationControllerOpeApi;
import joelmaciel.service_control.api.dto.ClientDTO;
import joelmaciel.service_control.api.dto.request.ClientRequestDTO;
import joelmaciel.service_control.api.security.dto.JwtDTO;
import joelmaciel.service_control.api.security.dto.LoginDTO;
import joelmaciel.service_control.domain.service.RegistrationClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController implements AuthenticationControllerOpeApi {

    private final RegistrationClientService clientService;

    @PostMapping("/signup")
    @PreAuthorize("hasAnyRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDTO registerClient(@RequestBody @Valid ClientRequestDTO clientRequestDTO) {
        return clientService.saveClient(clientRequestDTO);

    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtDTO authenticationClient(@RequestBody @Valid LoginDTO loginDTO) {
       return clientService.authenticationUserLogin(loginDTO);
    }
}
