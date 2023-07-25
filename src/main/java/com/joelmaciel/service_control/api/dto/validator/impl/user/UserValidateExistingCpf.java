package com.joelmaciel.service_control.api.dto.validator.impl.user;

import com.joelmaciel.service_control.api.dto.request.UserRequestDTO;
import com.joelmaciel.service_control.api.dto.validator.Validator;
import com.joelmaciel.service_control.domain.exception.CpfAlreadyExistsException;
import com.joelmaciel.service_control.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class UserValidateExistingCpf implements Validator<UserRequestDTO> {

    private final UserRepository userRepository;

    @Override
    public void validate(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByCpf(userRequestDTO.getCpf())) {
            throw new CpfAlreadyExistsException(userRequestDTO.getCpf());
        }
    }
}
