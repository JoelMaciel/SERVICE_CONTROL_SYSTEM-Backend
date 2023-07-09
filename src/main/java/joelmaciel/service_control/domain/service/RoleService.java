package joelmaciel.service_control.domain.service;

import joelmaciel.service_control.domain.enums.RoleType;
import joelmaciel.service_control.domain.exception.RoleNotExistsException;
import joelmaciel.service_control.domain.model.Roles;
import joelmaciel.service_control.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public Roles findByRoleName(RoleType roleType) {
        return roleRepository.findByRoleName(roleType)
                .orElseThrow(() -> new RoleNotExistsException(roleType));
    }
}
