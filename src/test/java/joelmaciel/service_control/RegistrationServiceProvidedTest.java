package joelmaciel.service_control;

import joelmaciel.service_control.api.dto.request.ClientIdRequestDTO;
import joelmaciel.service_control.api.dto.request.ServiceProvidedRequestDTO;
import joelmaciel.service_control.domain.exception.ClientNotFoundException;
import joelmaciel.service_control.domain.repository.ServiceProvidedRepository;
import joelmaciel.service_control.domain.service.RegistrationServiceProvidedService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class RegistrationServiceProvidedTest {

    @Autowired
    private RegistrationServiceProvidedService registrationServiceProvidedService;

    @Autowired
    private ServiceProvidedRepository serviceProvidedRepository;

    @Test
    public void shouldSuccessfullyRegisterServiceProvided() {
        ClientIdRequestDTO clientIdRequest = ClientIdRequestDTO.builder()
                .id(1L).build();

        ServiceProvidedRequestDTO serviceProvided = ServiceProvidedRequestDTO.builder()
                .description("Swap SSD memory")
                .price(new BigDecimal(200))
                .clientId(clientIdRequest)
                .build();
        registrationServiceProvidedService.save(serviceProvided);

        assertThat(serviceProvided).isNotNull();
        assertThat(serviceProvided.getDescription()).isNotNull();
        assertThat(serviceProvided.getPrice()).isNotNull();
        assertThat(serviceProvided.getClientId()).isNotNull();
    }

    @Test
    public void shouldFailToSaveServiceProvidedWithDescriptionNull() {
        ClientIdRequestDTO clientIdRequest = ClientIdRequestDTO.builder()
                .id(1L).build();

        ServiceProvidedRequestDTO serviceProvided = ServiceProvidedRequestDTO.builder()
                .price(new BigDecimal(100))
                .clientId(clientIdRequest)
                .build();

        DataIntegrityViolationException expectedError = Assertions.assertThrows(DataIntegrityViolationException.class,
                () -> {
                    registrationServiceProvidedService.save(serviceProvided);
                });
        assertThat(expectedError).isNotNull();

    }

    @Test
    public void shouldFailToSaveWithNullClientId() {
        ClientIdRequestDTO clientIdRequest = ClientIdRequestDTO.builder()
                .id(null).build();

        ServiceProvidedRequestDTO serviceProvided = ServiceProvidedRequestDTO.builder()
                .description("Swap SSD memory")
                .price(new BigDecimal(200))
                .clientId(clientIdRequest)
                .build();

        InvalidDataAccessApiUsageException expectedError = Assertions.assertThrows(InvalidDataAccessApiUsageException.class,
                () -> {
                    registrationServiceProvidedService.save(serviceProvided);
                });

        assertThat(expectedError).isNotNull();
    }

    @Test
    public void shouldFailToSaveWithClientIdThatDoesNotNExist() {
        ClientIdRequestDTO clientIdRequest = ClientIdRequestDTO.builder()
                .id(100L).build();

        ServiceProvidedRequestDTO serviceProvided = ServiceProvidedRequestDTO.builder()
                .description("Swap SSD memory")
                .price(new BigDecimal(200))
                .clientId(clientIdRequest)
                .build();

        ClientNotFoundException expectedError = Assertions.assertThrows(ClientNotFoundException.class,
                () -> {
                    registrationServiceProvidedService.save(serviceProvided);
                });

        assertThat(expectedError).isNotNull();
    }


}
