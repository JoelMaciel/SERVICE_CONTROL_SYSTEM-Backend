package joelmaciel.service_control;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import joelmaciel.service_control.api.dto.ClientDTO;
import joelmaciel.service_control.api.dto.ServiceProvidedDTO;
import joelmaciel.service_control.api.dto.request.ClientIdRequestDTO;
import joelmaciel.service_control.api.dto.request.ClientRequestDTO;
import joelmaciel.service_control.api.dto.request.ServiceProvidedRequestDTO;
import joelmaciel.service_control.domain.repository.ServiceProvidedRepository;
import joelmaciel.service_control.domain.service.RegistrationClientService;
import joelmaciel.service_control.domain.service.RegistrationServiceProvidedService;
import joelmaciel.service_control.util.DatabaseCleaner;
import joelmaciel.service_control.util.ResourceUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class RegistrationServiceProvideE2EIT {

    private static final String INVALID_DATA = "Invalid Data";

    private static final String CLIENT_NOT_FOUND = "Resource Not Found";
    private static final Long CLIENT_ID_NOT_EXISTENT = 100L;

    @LocalServerPort
    private int port;

    private ServiceProvidedDTO serviceProvidedDTO;
    private int quantityClientsRegistered;
    private String jsonCorrectServiceProvided;
    private String jsonCorrectServiceProvidedtWithoutPrice;

    private String jsonServiceProvidedNotPassClientId;

    private String jsonCorrectServiceProvidedtWithoutDescription;
    private String jsonServiceProvidedNotFoundClientId;
    @Autowired
    private  RegistrationClientService clientService;

    @Autowired
    private ServiceProvidedRepository serviceProvidedRepository;

    @Autowired
    private RegistrationServiceProvidedService registrationServiceProvidedService;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/api/services-provided";

        jsonCorrectServiceProvided = ResourceUtils.getContentFromResource(
                "/json/correct/service-provided-data.json"
        );

        jsonCorrectServiceProvidedtWithoutPrice = ResourceUtils.getContentFromResource(
                "/json/incorrect/service-provided-without-price.json"
        );

        jsonServiceProvidedNotPassClientId = ResourceUtils.getContentFromResource(
                "/json/incorrect/service-provided-not-pass-client-id.json"
        );
        jsonServiceProvidedNotFoundClientId = ResourceUtils.getContentFromResource(
                "/json/incorrect/service-provided-not-found-client-id.json"
        );

        jsonCorrectServiceProvidedtWithoutDescription = ResourceUtils.getContentFromResource(
                "/json/incorrect/service-provided-without-description.json"
        );

        databaseCleaner.clearTables();
        prepareData();
    }

    @Test
    public void shouldReturnStatus200_WhenConsultingServiceProvided() {
        given()
                .accept(ContentType.JSON)
              .when()
                .get()
              .then().statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldReturnCorrectQuantityOfServiceProvided_WhenConsultingServiceProvided() {
        given()
                .accept(ContentType.JSON)
              .when()
                .get("?month=7&name=")
              .then()
                .body("", hasSize(quantityClientsRegistered));
    }

    @Test
    public void shouldReturnStatus201_WhenRegisteringServiceProvided() {
        given()
                .body(jsonCorrectServiceProvided)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
              .when()
                .post()
              .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("description", equalTo("PC formatting"))
                .body("price", equalTo(1800));
    }

    @Test
    public void shouldReturnStatus404Correct_WhenNotFoundClientId() {
        given()
                .body(jsonServiceProvidedNotFoundClientId)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
              .when()
                .post()
              .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("title", equalTo(CLIENT_NOT_FOUND));
    }

    @Test
    public void shouldReturnStatus400Correct_WhenNotPassTheClientId() {
        given()
                .body(jsonServiceProvidedNotPassClientId)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
              .when()
                .post()
              .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(INVALID_DATA));
    }

    @Test
    public void shouldReturnStatus400Correct_WhenRegisterWithoutPrice() {
        given()
                .body(jsonCorrectServiceProvidedtWithoutPrice)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
              .when()
                .post()
              .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(INVALID_DATA));
    }

    @Test
    public void shouldReturnStatus400_WhenRegisterWithoutDescription() {
        given()
                .body(jsonCorrectServiceProvidedtWithoutDescription)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
              .when()
                .post()
              .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(INVALID_DATA));

    }

    private void prepareData() {
        ClientIdRequestDTO clientIdRequestDTO = ClientIdRequestDTO.builder()
                .id(1L)
                .build();

        ClientRequestDTO clientRequestDTO = ClientRequestDTO.builder()
                .name("Sampaio")
                .cpf("53366221097")
                .build();

      ClientDTO client = clientService.saveClient(clientRequestDTO);


        ServiceProvidedRequestDTO serviceProvide = ServiceProvidedRequestDTO.builder()
                .description("Change mouse")
                .price(new BigDecimal(50))
                .clientId(clientIdRequestDTO)
                .build();

        registrationServiceProvidedService.save(serviceProvide);

        ServiceProvidedRequestDTO serviceProvidedRequestDTO = ServiceProvidedRequestDTO.builder()
                .description("Faulty monitor screen")
                .price(new BigDecimal(330))
                .clientId(clientIdRequestDTO)
                .build();

        serviceProvidedDTO = registrationServiceProvidedService.save(serviceProvidedRequestDTO);

        quantityClientsRegistered = (int) serviceProvidedRepository.count();
    }

}
