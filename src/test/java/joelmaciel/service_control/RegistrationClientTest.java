package joelmaciel.service_control;

import joelmaciel.service_control.api.dto.request.ClientRequestDTO;
import joelmaciel.service_control.domain.exception.ClientNotFoundException;
import joelmaciel.service_control.domain.model.Client;
import joelmaciel.service_control.domain.repository.ClientRepository;
import joelmaciel.service_control.domain.service.RegistrationClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RegistrationClientTest {

    @Autowired
    private RegistrationClientService registrationClientService;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void shouldSucceedWhenFetchingClientById() {
        ClientRequestDTO clientDTO = ClientRequestDTO.builder()
                .name("Joel")
                .cpf("28816044047")
                .email("joel02@bol.com")
                .build();
        Client client = ClientRequestDTO.toModel(clientDTO);
        clientRepository.save(client);
        Long clientId = client.getId();

        Client foundClient = clientRepository.findById(clientId).orElse(null);

        Assertions.assertNotNull(foundClient);
        Assertions.assertEquals("Joel", foundClient.getName());
    }

    @Test
    public void shouldSuccessfullyRegisterClient() {
        ClientRequestDTO client = ClientRequestDTO.builder()
                .name("Alex Mario")
                .cpf("39669067081")
                .email("alex@gmail.com")
                .build();
        registrationClientService.saveClient(client);

        assertThat(client).isNotNull();
        assertThat(client.getCpf()).isNotNull();
        assertThat(client.getEmail()).isNotNull();
        assertThat(client.getName()).isNotNull();
    }

    @Test
    public void shouldFailToSaveClientWithoutNameAndCPF() {
        ClientRequestDTO client = ClientRequestDTO.builder()
                .name(null)
                .cpf(null)
                .build();

        DataIntegrityViolationException expectedError = Assertions.assertThrows(DataIntegrityViolationException.class,
                () -> {
                    registrationClientService.saveClient(client);
                });
        assertThat(expectedError).isNotNull();
    }

    @Test
    public void shouldFailedToDeleteAClientThatDoesNotExist() {
        ClientNotFoundException expectedError = Assertions.assertThrows(ClientNotFoundException.class,
                () -> {
                    registrationClientService.removeClient(10L);
                });

        assertThat(expectedError).isNotNull();
    }


}
