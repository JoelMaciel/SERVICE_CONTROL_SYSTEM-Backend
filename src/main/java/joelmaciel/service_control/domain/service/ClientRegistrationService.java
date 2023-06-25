package joelmaciel.service_control.domain.service;

import joelmaciel.service_control.domain.exception.ClientNotFoundException;
import joelmaciel.service_control.domain.model.Client;
import joelmaciel.service_control.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClientRegistrationService {

    private final ClientRepository clientRepository;

    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    public Client findById(Long clientId) {
        return searchById(clientId);
    }

    @Transactional
    public Client updateClient(Long clientId, @RequestBody Client client) {
        Client currentClient = searchById(clientId).toBuilder()
                .name(client.getName())
                .cpf(client.getCpf())
                .build();

        return clientRepository.save(currentClient);
    }

    @Transactional
    public Client saveClient(Client client) {
        Client client1 = new Client();
        return clientRepository.save(client);
    }

    @Transactional
    public void removeClient(Long clientId) {
        searchById(clientId);
        clientRepository.deleteById(clientId);
    }

    public Client searchById(Long clientId) {
        return clientRepository.findById(clientId).orElseThrow(() ->
                new ClientNotFoundException(clientId));
    }

}
