package joelmaciel.service_control;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import joelmaciel.service_control.api.dto.ClientDTO;
import joelmaciel.service_control.api.dto.request.ClientRequestDTO;
import joelmaciel.service_control.domain.repository.ClientRepository;
import joelmaciel.service_control.domain.service.RegistrationClientService;
import joelmaciel.service_control.util.DatabaseCleaner;
import joelmaciel.service_control.util.ResourceUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class RegistrationClientE2EIT {

    private static final String INVALID_DATA = "Invalid Data";
    private static final Long CLIENT_ID_NOT_EXISTENT = 100L;

    @LocalServerPort
    private int port;

    private ClientDTO clientDTO;
    private int quantityClientsRegistered;
    private String jsonCorrectClient;
    private String jsonClientWhitInvalidCpf;

    private String jsonClientWithNullCpf;

    private String jsonClientWithNullName;
    @Autowired
    private  ClientRepository clientRepository;

    @Autowired
    private RegistrationClientService clientService;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/api/clients";

        jsonCorrectClient = ResourceUtils.getContentFromResource(
                "/json/correct/client-data.json"
        );

        jsonClientWhitInvalidCpf = ResourceUtils.getContentFromResource(
                "/json/incorrect/client-invalid-cpf.json"
        );

        jsonClientWhitInvalidCpf = ResourceUtils.getContentFromResource(
                "json/incorrect/client-invalid-cpf.json"
        );

        jsonClientWithNullCpf = ResourceUtils.getContentFromResource(
                "json/incorrect/client-null-cpf.json"
        );

        jsonClientWithNullName = ResourceUtils.getContentFromResource(
                "json/incorrect/client-null-name.json"
        );

        databaseCleaner.clearTables();
        prepareData();
    }

    @Test
    public void shouldReturnStatus200_WhenConsultingClient() {
        given()
                .accept(ContentType.JSON)
              .when()
                .get()
              .then().statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldReturnCorrectQuantityOfClient_WhenConsultingClients() {
        given()
                .accept(ContentType.JSON)
              .when()
                .get()
              .then()
                .body("", hasSize(quantityClientsRegistered));
    }

    @Test
    public void shouldReturnStatus201_WhenRegisteringClient() {
        given()
                .body(jsonCorrectClient)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
              .when()
                .post()
              .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void shouldReturnStatus404Correct_WhenQueryNonexistentClient() {
        given()
                .pathParams("clientId", CLIENT_ID_NOT_EXISTENT)
                .accept(ContentType.JSON)
              .when()
                .get("/{clientId}")
              .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void shouldReturnStatus400_WhenRegisterClientWithNullName() {
        given()
                .body(jsonClientWithNullName)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
              .when()
                .post()
              .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void shouldReturnStatus400_WhenRegisterClientWithoutCpf() {
        given()
                .body(jsonClientWithNullCpf)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
              .when()
                .post()
              .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    public void shouldReturnStatus400_WhenRegisterClientWithInvalidCpf() {
        given()
                .body(jsonClientWhitInvalidCpf)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
              .when()
                .post()
              .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }


    private void prepareData() {
        ClientRequestDTO clientRequestDTO = ClientRequestDTO.builder()
                .name("Raimundo")
                .cpf("39669067081")
                .build();

        clientService.saveClient(clientRequestDTO);

        ClientRequestDTO clientRequestDTO1 = ClientRequestDTO.builder()
                .name("Bruno Alves")
                .cpf("49952369026")
                .build();

         clientDTO = clientService.saveClient(clientRequestDTO1);

        quantityClientsRegistered = (int) clientRepository.count();
    }

}
