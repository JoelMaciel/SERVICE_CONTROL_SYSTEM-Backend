package joelmaciel.service_control.api.controller;

import joelmaciel.service_control.domain.model.Client;
import joelmaciel.service_control.domain.service.ClientRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientRegistrationService clientRegistrationService;

    @GetMapping
    public List<Client> allClients() {
        return clientRegistrationService.findAllClients();
    }

    @GetMapping("/{clientId}")
    public Client getOne(@PathVariable Long clientId) {
        return clientRegistrationService.findById(clientId);
    }

    @PutMapping("/{clientId}")
    public Client update(@PathVariable Long clientId, @RequestBody @Valid Client client) {
        return clientRegistrationService.updateClient(clientId, client);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client save(@RequestBody @Valid Client client) {
        return clientRegistrationService.saveClient(client);

    }

    @DeleteMapping("/{clientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long clientId) {
        clientRegistrationService.removeClient(clientId);
    }
}
