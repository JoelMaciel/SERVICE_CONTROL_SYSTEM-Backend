package joelmaciel.service_control.domain.repository;

import joelmaciel.service_control.domain.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
}
