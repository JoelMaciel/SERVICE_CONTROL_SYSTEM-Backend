package joelmaciel.service_control.domain.service;

import joelmaciel.service_control.domain.exception.ClientNotFoundException;
import joelmaciel.service_control.domain.model.Client;
import joelmaciel.service_control.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClientRegistrationService {

    private final ClientRepository clientRepository;

    @Transactional
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public Client findById(Long clientId) {
        return searchById(clientId);
    }

    public Client searchById(Long clientId) {
        return clientRepository.findById(clientId).orElseThrow(() ->
                new ClientNotFoundException(clientId));
    }
}
