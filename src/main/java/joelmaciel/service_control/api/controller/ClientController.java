package joelmaciel.service_control.api.controller;

import joelmaciel.service_control.api.controller.openapi.ClientControllerOpenApi;
import joelmaciel.service_control.api.dto.ClientDTO;
import joelmaciel.service_control.api.dto.request.ClientRequestDTO;
import joelmaciel.service_control.api.dto.request.ClientRequestUpdateDTO;
import joelmaciel.service_control.domain.service.RegistrationClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clients")
public class ClientController implements ClientControllerOpenApi {

    private final RegistrationClientService registrationClientService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER')")
    public List<ClientDTO> getAllClients() {
        return registrationClientService.findAllClients();
    }

    @GetMapping("/{clientId}")
    @PreAuthorize("hasAnyRole('USER')")
    public ClientDTO getOneClient(@PathVariable Long clientId) {
        return registrationClientService.findById(clientId);
    }

    @PutMapping("/{clientId}")
    @PreAuthorize("hasAnyRole('USER')")
    public ClientDTO update(@PathVariable Long clientId, @RequestBody @Valid ClientRequestUpdateDTO clientRequestDTO) {
        return registrationClientService.updateClient(clientId, clientRequestDTO);
    }


    @DeleteMapping("/{clientId}")
    @PreAuthorize("hasAnyRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long clientId) {
        registrationClientService.removeClient(clientId);
    }
}
