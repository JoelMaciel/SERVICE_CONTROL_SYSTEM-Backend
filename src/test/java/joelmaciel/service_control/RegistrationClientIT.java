package joelmaciel.service_control;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import joelmaciel.service_control.api.dto.ClientDTO;
import joelmaciel.service_control.api.dto.UserDTO;
import joelmaciel.service_control.api.dto.request.ClientRequestDTO;
import joelmaciel.service_control.api.security.dto.LoginDTO;
import joelmaciel.service_control.domain.repository.ClientRepository;
import joelmaciel.service_control.domain.repository.UserRepository;
import joelmaciel.service_control.domain.service.RegistrationClientService;
import joelmaciel.service_control.domain.service.RegistrationUserService;
import joelmaciel.service_control.domain.service.RoleService;
import joelmaciel.service_control.util.DatabaseCleaner;
import joelmaciel.service_control.util.LoginData;
import joelmaciel.service_control.util.ResourceUtils;
import joelmaciel.service_control.util.TokenUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class RegistrationClientIT {

    private static final String INVALID_DATA = "Invalid Data";
    private static final Long CLIENT_ID_NOT_EXISTENT = 100L;
    private static final String CLIENT_NOT_FOUND = "Resource Not Found";


    @LocalServerPort
    private int port;

    private ClientDTO clientDTO;
    private LoginDTO loginDTO;

    private int quantityClientsRegistered;
    private String jsonCorrectClient;
    private String jsonLoginCorrect;
    private String jsonClientWhitInvalidCpf;

    private String jsonClientWithNullCpf;

    private String jsonClientWithNullName;

    private String token;

    @Autowired
    private RoleService roleService;

    private UserDTO userDTO;
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegistrationUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RegistrationClientService clientService;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/api";

        jsonLoginCorrect = ResourceUtils.getContentFromResource(
                "/json/correct/login-dto.json");

        jsonCorrectClient = ResourceUtils.getContentFromResource(
                "/json/correct/client-data.json");

        jsonClientWhitInvalidCpf = ResourceUtils.getContentFromResource(
                "/json/incorrect/client-invalid-cpf.json");

        jsonClientWithNullCpf = ResourceUtils.getContentFromResource(
                "/json/incorrect/client-null-cpf.json");

        jsonClientWithNullName = ResourceUtils.getContentFromResource(
                "/json/incorrect/client-null-name.json");

        token = TokenUtils.getToken(port, jsonLoginCorrect);
        databaseCleaner.clearTables();
        prepareData();

    }


    @Test
    public void shouldReturnStatus200_WhenConsultingClient() {
        given()
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/clients")
                .then()
                .statusCode(HttpStatus.OK.value());

    }

    @Test
    public void

    shouldReturnCorrectQuantityOfClient_WhenConsultingClients() {
        given()
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/clients")
                .then()
                .body("", hasSize(quantityClientsRegistered));
    }

    @Test
    public void shouldReturnStatus201_WhenRegisteringClient() {
        given()
                .body(jsonCorrectClient)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .accept(ContentType.JSON)
                .when()
                .post("/clients")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("name", equalTo("Aloisio Martins"));

    }

    @Test
    public void shouldReturnStatus404Correct_WhenQueryNonexistentClient() {
        given()
                .pathParams("clientId", CLIENT_ID_NOT_EXISTENT)
                .accept(ContentType.JSON).header("Authorization", "Bearer " + token)
                .when()
                .get("/clients/{clientId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("title", equalTo(CLIENT_NOT_FOUND));
    }

    @Test
    public void shouldReturnStatus400_WhenRegisterClientWithNullName() {
        given()
                .body(jsonClientWithNullName)
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when().post("/clients")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(INVALID_DATA));
    }


    @Test
    public void shouldReturnStatus400_WhenRegisterClientWithoutCpf() {
        given()
                .body(jsonClientWithNullCpf)
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when().post("/clients")
                .then().statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(INVALID_DATA));

    }

    @Test
    public void shouldReturnStatus400_WhenRegisterClientWithInvalidCpf() {
        given()
                .body(jsonClientWhitInvalidCpf)
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/clients")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(INVALID_DATA));
    }


    private void prepareData() {

        LoginData loginData = new LoginData(roleService, userRepository, passwordEncoder);
        loginData.initializeRoles();
        userDTO = loginData.createUser();
        loginDTO = loginData.createLogin();

        ClientRequestDTO clientRequestDTO = ClientRequestDTO.builder()
                .name("Raimundo")
                .cpf("39669067081")
                .email("teste@teste.com")
                .build();

        clientService.saveClient(clientRequestDTO);

        clientRequestDTO = ClientRequestDTO.builder()
                .name("Bruno Alves")
                .cpf("49952369026")
                .email("teste@bol.com")
                .build();

        clientDTO = clientService.saveClient(clientRequestDTO);

        quantityClientsRegistered = (int) clientRepository.count();

    }

}
