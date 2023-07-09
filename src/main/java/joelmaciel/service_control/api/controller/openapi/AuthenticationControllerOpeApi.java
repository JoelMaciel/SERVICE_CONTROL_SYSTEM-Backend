package joelmaciel.service_control.api.controller.openapi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

@Api(tags = "Authentication")
public interface AuthenticationControllerOpeApi {

    @ApiOperation("Save a client")
    ClientDTO registerClient(@RequestBody @Valid ClientRequestDTO clientRequestDTO);


    @ApiOperation("Client login")
    JwtDTO authenticationClient(@RequestBody @Valid LoginDTO loginDTO) ;
}
