package joelmaciel.service_control.api.controller;

import joelmaciel.service_control.api.dto.ClientDTO;
import joelmaciel.service_control.api.dto.request.ClientRequestDTO;
import joelmaciel.service_control.domain.service.ClientRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientRegistrationService clientRegistrationService;

    @GetMapping
    public List<ClientDTO> allClients() {
        return clientRegistrationService.findAllClients();
    }

    @GetMapping("/{clientId}")
    public ClientDTO getOne(@PathVariable Long clientId) {
        return clientRegistrationService.findById(clientId);
    }

    @PutMapping("/{clientId}")
    public ClientDTO update(@PathVariable Long clientId, @RequestBody @Valid ClientRequestDTO clientRequestDTO) {
        return clientRegistrationService.updateClient(clientId, clientRequestDTO);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDTO save(@RequestBody @Valid ClientRequestDTO clientRequestDTO) {
        return clientRegistrationService.saveClient(clientRequestDTO);

    }

    @DeleteMapping("/{clientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long clientId) {
        clientRegistrationService.removeClient(clientId);
    }
}
