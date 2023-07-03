package joelmaciel.service_control.domain.service;

import joelmaciel.service_control.api.dto.request.UserRequestDTO;
import joelmaciel.service_control.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public void save(UserRequestDTO userRequest) {
        userRepository.save(UserRequestDTO.toModel(userRequest));
    }
}
