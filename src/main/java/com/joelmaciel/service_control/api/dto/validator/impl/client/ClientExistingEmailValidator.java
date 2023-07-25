package com.joelmaciel.service_control.api.dto.validator.impl.client;

import com.joelmaciel.service_control.api.dto.request.ClientRequestDTO;
import com.joelmaciel.service_control.api.dto.validator.Validator;
import com.joelmaciel.service_control.domain.exception.EmailAlreadyExistsException;
import com.joelmaciel.service_control.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientExistingEmailValidator implements Validator<ClientRequestDTO> {

    private final ClientRepository clientRepository;


    @Override
    public void validate(ClientRequestDTO clientRequestDTO) {
        if (clientRepository.existsByEmail(clientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException(clientRequestDTO.getEmail());
        }
    }
}
