package com.joelmaciel.service_control.domain.service;

import com.joelmaciel.service_control.domain.model.Client;
import com.joelmaciel.service_control.api.dto.ClientDTO;
import com.joelmaciel.service_control.api.dto.request.ClientRequestDTO;
import com.joelmaciel.service_control.api.dto.request.ClientRequestUpdateDTO;
import com.joelmaciel.service_control.api.dto.validator.Validator;
import com.joelmaciel.service_control.domain.exception.ClientNotFoundException;
import com.joelmaciel.service_control.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RegistrationClientService {

    private final ClientRepository clientRepository;
    private final List<Validator<ClientRequestDTO>> listClientValidator;

    public List<ClientDTO> findAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(ClientDTO::toDTO)
                .toList();
    }

    public ClientDTO findById(Long clientId) {
        Client client = searchById(clientId);
        return ClientDTO.toDTO(client);
    }

    @Transactional
    public ClientDTO updateClient(Long clientId, ClientRequestUpdateDTO clientRequestDTO) {
        Client currentClient = searchById(clientId).toBuilder()
                .name(clientRequestDTO.getName())
                .email(clientRequestDTO.getEmail())
                .build();

        return ClientDTO.toDTO(clientRepository.save(currentClient));
    }

    @Transactional
    public ClientDTO saveClient(ClientRequestDTO clientRequestDTO) {
        listClientValidator.forEach(validator -> validator.validate(clientRequestDTO));

        return ClientDTO.toDTO(clientRepository.save(ClientRequestDTO.toModel(clientRequestDTO)));
    }

    @Transactional
    public void removeClient(Long clientId) {
        searchById(clientId);
        clientRepository.deleteById(clientId);
    }

    public Client searchById(Long clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException(clientId));
    }
}
