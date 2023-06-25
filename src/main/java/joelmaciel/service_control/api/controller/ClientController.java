package joelmaciel.service_control.api.controller;

import joelmaciel.service_control.domain.model.Client;
import joelmaciel.service_control.domain.service.ClientRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientRegistrationService clientRegistrationService;

    @GetMapping("/{clientId}")
    public Client getOne(@PathVariable Long clientId) {
        return clientRegistrationService.findById(clientId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client save(@RequestBody Client client) {
        return clientRegistrationService.save(client);

    }
}
