package joelmaciel.service_control.domain.repository;

import joelmaciel.service_control.domain.enums.RoleType;
import joelmaciel.service_control.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleType roleType);
}
