package joelmaciel.service_control.domain.service;

import joelmaciel.service_control.domain.enums.RoleType;
import joelmaciel.service_control.domain.exception.RoleNotExistsException;
import joelmaciel.service_control.domain.model.Role;
import joelmaciel.service_control.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional
    public Role save(Role role) {
//        Role newRole = Role.builder()
//                .roleId(1L)
//                .roleName(RoleType.ROLE_ADMIN)
//                .build();
        return roleRepository.save(role);
    }

    public Role findByRoleName(RoleType roleType) {
        return roleRepository.findByRoleName(roleType)
                .orElseThrow(() -> new RoleNotExistsException(roleType));
    }
}
