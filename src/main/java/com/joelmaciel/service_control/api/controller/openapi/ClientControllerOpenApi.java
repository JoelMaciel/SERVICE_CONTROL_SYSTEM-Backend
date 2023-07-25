package com.joelmaciel.service_control.api.controller.openapi;

import com.joelmaciel.service_control.api.dto.request.ClientRequestUpdateDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.joelmaciel.service_control.api.dto.ClientDTO;
import com.joelmaciel.service_control.api.dto.request.ClientRequestDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Clients")
public interface ClientControllerOpenApi {

    @ApiOperation("Search all clients")
    List<ClientDTO> getAllClients();

    @ApiOperation("Search by client Id")
    ClientDTO getOneClient(@PathVariable Long clientId) ;

    @ApiOperation("Update a client")
    ClientDTO update(@PathVariable Long clientId, @RequestBody @Valid ClientRequestUpdateDTO clientRequestDTO);

    ClientDTO saveClient(@RequestBody @Valid ClientRequestDTO clientRequestDTO);
    @ApiOperation("Delete a client")
     void delete(@PathVariable Long clientId);
}
