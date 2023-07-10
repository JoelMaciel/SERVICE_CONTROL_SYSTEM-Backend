package joelmaciel.service_control.api.dto;

import joelmaciel.service_control.api.dto.request.UserRequestDTO;
import joelmaciel.service_control.domain.exception.CpfAlreadyExistsException;
import joelmaciel.service_control.domain.exception.EmailAlreadyExistsException;
import joelmaciel.service_control.domain.exception.UsernameAlreadyExistsException;
import joelmaciel.service_control.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserValidator {

    private final UserRepository userRepository;

    public void validateExistingCpf(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByCpf(userRequestDTO.getCpf())) {
            throw new CpfAlreadyExistsException(userRequestDTO.getCpf());
        }
    }

    public void validateUsername(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new UsernameAlreadyExistsException(userRequestDTO.getUsername());
        }
    }
    public void validateEmail(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException(userRequestDTO.getEmail());
        }
    }

}
