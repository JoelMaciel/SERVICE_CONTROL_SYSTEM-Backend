package joelmaciel.service_control.api.controller.openapi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import joelmaciel.service_control.api.dto.ClientDTO;
import joelmaciel.service_control.api.dto.request.ClientRequestDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Clients")
public interface ClientControllerOpenApi {

    @ApiOperation("Search all clients")
    List<ClientDTO> allClients();

    @ApiOperation("Search by client Id")
    ClientDTO getOne(@PathVariable Long clientId) ;

    @ApiOperation("Update a client")
    ClientDTO update(@PathVariable Long clientId, @RequestBody @Valid ClientRequestDTO clientRequestDTO);

    @ApiOperation("Save a client")
     ClientDTO save(@RequestBody @Valid ClientRequestDTO clientRequestDTO) ;

    @ApiOperation("Delete a client")
     void delete(@PathVariable Long clientId);
}
