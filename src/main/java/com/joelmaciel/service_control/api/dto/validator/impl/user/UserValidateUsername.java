package com.joelmaciel.service_control.api.dto.validator.impl.user;

import com.joelmaciel.service_control.api.dto.request.UserRequestDTO;
import com.joelmaciel.service_control.api.dto.validator.Validator;
import com.joelmaciel.service_control.domain.exception.UsernameAlreadyExistsException;
import com.joelmaciel.service_control.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class UserValidateUsername implements Validator<UserRequestDTO> {

    private final UserRepository userRepository;

    @Override
    public void validate(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new UsernameAlreadyExistsException(userRequestDTO.getUsername());
        }
    }
}
