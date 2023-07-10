package joelmaciel.service_control.domain.repository;

import joelmaciel.service_control.domain.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);

}
