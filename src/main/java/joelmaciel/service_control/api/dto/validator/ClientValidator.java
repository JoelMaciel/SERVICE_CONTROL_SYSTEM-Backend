package joelmaciel.service_control.api.dto.validator;

import joelmaciel.service_control.api.dto.request.ClientRequestDTO;
import joelmaciel.service_control.domain.exception.CpfAlreadyExistsException;
import joelmaciel.service_control.domain.exception.EmailAlreadyExistsException;
import joelmaciel.service_control.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ClientValidator {

    private final ClientRepository clientRepository;

    public void validateExistingCpf(ClientRequestDTO clientRequestDTO) {
        if (clientRepository.existsByCpf(clientRequestDTO.getCpf())) {
            throw new CpfAlreadyExistsException(clientRequestDTO.getCpf());
        }
    }

    public void validateEmail(ClientRequestDTO clientRequestDTO) {
        if (clientRepository.existsByEmail(clientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException(clientRequestDTO.getEmail());
        }
    }

}
