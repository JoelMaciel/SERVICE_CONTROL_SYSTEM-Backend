package joelmaciel.service_control.domain.repository;

import joelmaciel.service_control.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
