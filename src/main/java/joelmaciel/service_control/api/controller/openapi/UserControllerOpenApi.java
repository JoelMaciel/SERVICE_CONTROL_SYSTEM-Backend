package joelmaciel.service_control.api.controller.openapi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import joelmaciel.service_control.api.dto.ClientDTO;
import joelmaciel.service_control.api.dto.UserDTO;
import joelmaciel.service_control.api.dto.request.ClientRequestUpdateDTO;
import joelmaciel.service_control.api.dto.request.UserRequestUpdateDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Users")
public interface UserControllerOpenApi {

    @ApiOperation("Search all users")
    List<UserDTO> getAllUsers();

    @ApiOperation("Search by user Id")
    UserDTO getOneUser(@PathVariable Long userId);

    @ApiOperation("Update a user")
    UserDTO update(@PathVariable Long userId, @RequestBody @Valid UserRequestUpdateDTO userRequestDTO);

    @ApiOperation("Delete a user")
    void delete(@PathVariable Long userId);
}
