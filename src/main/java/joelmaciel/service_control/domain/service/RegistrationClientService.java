package joelmaciel.service_control.domain.service;

import joelmaciel.service_control.api.dto.ClientDTO;
import joelmaciel.service_control.api.dto.request.ClientRequestDTO;
import joelmaciel.service_control.api.dto.request.ClientRequestUpdateDTO;
import joelmaciel.service_control.api.dto.validator.ClientValidator;
import joelmaciel.service_control.domain.exception.ClientNotFoundException;
import joelmaciel.service_control.domain.model.Client;
import joelmaciel.service_control.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RegistrationClientService {

    private final ClientRepository clientRepository;
    private final ClientValidator clientValidator;

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
    public ClientDTO updateClient(Long clientId, ClientRequestUpdateDTO clientRequestDTO) {
        searchById(clientId);
        Client currentClient = searchById(clientId).toBuilder()
                .name(clientRequestDTO.getName())
                .email(clientRequestDTO.getEmail())
                .build();

        return ClientDTO.toDTO(clientRepository.save(currentClient));
    }

    @Transactional
    public ClientDTO saveClient(ClientRequestDTO clientRequestDTO) {
        clientValidator.validateExistingCpf(clientRequestDTO);
        clientValidator.validateEmail(clientRequestDTO);

        return ClientDTO.toDTO(clientRepository.save(ClientRequestDTO.toModel(clientRequestDTO)));
    }

    @Transactional
    public void removeClient(Long clientId) {
        searchById(clientId);
        clientRepository.deleteById(clientId);
    }

    public Client searchById(Long clientId) {
        return clientRepository.findById(clientId).orElseThrow(
                () -> new ClientNotFoundException(clientId));
    }
}
