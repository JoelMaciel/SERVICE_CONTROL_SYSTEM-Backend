package joelmaciel.service_control.api.controller;

import joelmaciel.service_control.api.controller.openapi.AuthenticationControllerOpeApi;
import joelmaciel.service_control.api.dto.UserDTO;
import joelmaciel.service_control.api.dto.request.UserRequestDTO;
import joelmaciel.service_control.api.security.dto.JwtDTO;
import joelmaciel.service_control.api.security.dto.LoginDTO;
import joelmaciel.service_control.domain.service.RegistrationUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController implements AuthenticationControllerOpeApi {

    private final RegistrationUserService  userService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO registerUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        return userService.saveUser(userRequestDTO);

    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtDTO authenticationUser(@RequestBody @Valid LoginDTO loginDTO) {
       return userService.authenticationUserLogin(loginDTO);
    }


}
