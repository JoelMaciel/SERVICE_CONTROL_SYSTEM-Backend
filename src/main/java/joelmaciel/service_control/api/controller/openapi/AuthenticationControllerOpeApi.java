package joelmaciel.service_control.api.controller.openapi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import joelmaciel.service_control.api.dto.UserDTO;
import joelmaciel.service_control.api.dto.request.UserRequestDTO;
import joelmaciel.service_control.api.security.dto.JwtDTO;
import joelmaciel.service_control.api.security.dto.LoginDTO;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(tags = "Authentication")
public interface AuthenticationControllerOpeApi {

    @ApiOperation("Save a user")
    public UserDTO registerUser(@RequestBody @Valid UserRequestDTO userRequestDTO);


    @ApiOperation("User login")
    public JwtDTO authenticationUser(@RequestBody @Valid LoginDTO loginDTO) ;
}
