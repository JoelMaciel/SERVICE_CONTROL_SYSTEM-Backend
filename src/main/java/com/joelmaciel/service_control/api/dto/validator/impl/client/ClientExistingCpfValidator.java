package com.joelmaciel.service_control.api.dto.validator.impl.client;

import com.joelmaciel.service_control.api.dto.request.ClientRequestDTO;
import com.joelmaciel.service_control.api.dto.validator.Validator;
import com.joelmaciel.service_control.domain.exception.CpfAlreadyExistsException;
import com.joelmaciel.service_control.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientExistingCpfValidator implements Validator<ClientRequestDTO> {

    private final ClientRepository clientRepository;

    @Override
    public void validate(ClientRequestDTO clientRequestDTO) {
        if (clientRepository.existsByCpf(clientRequestDTO.getCpf())) {
            throw new CpfAlreadyExistsException(clientRequestDTO.getCpf());
        }
    }
}
