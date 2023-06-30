package joelmaciel.service_control.domain.service;

import joelmaciel.service_control.api.dto.ClientDTO;
import joelmaciel.service_control.api.dto.request.ClientRequestDTO;
import joelmaciel.service_control.domain.exception.ClientNotFoundException;
import joelmaciel.service_control.domain.exception.CpfAlreadyExistsException;
import joelmaciel.service_control.domain.model.Client;
import joelmaciel.service_control.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ClientRegistrationService {

    private final ClientRepository clientRepository;

    public List<ClientDTO> findAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream().map(client -> ClientDTO.toDTO(client))
                .collect(Collectors.toList());
    }

    public ClientDTO findById(Long clientId) {
        Client client = searchById(clientId);
        return ClientDTO.toDTO(client);
    }

    @Transactional
    public ClientDTO updateClient(Long clientId, @RequestBody ClientRequestDTO clientRequestDTO) {
        Client currentClient = searchById(clientId).toBuilder()
                .name(clientRequestDTO.getName())
                .cpf(clientRequestDTO.getCpf())
                .build();

        return ClientDTO.toDTO(clientRepository.save(currentClient));
    }

    @Transactional
    public ClientDTO saveClient(ClientRequestDTO clientRequestDTO) {
        validateExistingCpf(clientRequestDTO);
        Client client = ClientRequestDTO.toModel(clientRequestDTO);
        return ClientDTO.toDTO(clientRepository.save(client));
    }

    @Transactional
    public void removeClient(Long clientId) {
        searchById(clientId);
        clientRepository.deleteById(clientId);
    }

    private void validateExistingCpf(ClientRequestDTO clientRequestDTO) {
        if(clientRepository.existsByCpf(clientRequestDTO.getCpf())) {
            throw new CpfAlreadyExistsException(clientRequestDTO.getCpf());
        }
    }

    public Client searchById(Long clientId) {
        return clientRepository.findById(clientId).orElseThrow(
                () -> new ClientNotFoundException(clientId));
    }


}
