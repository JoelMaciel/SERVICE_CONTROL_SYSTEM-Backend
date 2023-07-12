package joelmaciel.service_control;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import joelmaciel.service_control.api.dto.UserDTO;
import joelmaciel.service_control.api.security.dto.LoginDTO;
import joelmaciel.service_control.domain.repository.UserRepository;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class RegistrationUserIT {

    @LocalServerPort
    private int port;
    private String token;
    private UserDTO userDTO;
    private LoginDTO loginDTO;
    private String jsonLoginCorrect;
    private String jsonUserCorrect;

    @Autowired
    private DatabaseCleaner databaseCleaner;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/api/users";

        jsonLoginCorrect = ResourceUtils.getContentFromResource(
                "/json/correct/login-dto.json");


        token = TokenUtils.getToken(port, jsonLoginCorrect);
        databaseCleaner.clearTables();
        prepareData();
    }

    @Test
    public void shouldReturn201_WhenSaveUserSuccessfully() {
        given()
                .body(jsonUserCorrect)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
              .when()
                .post()
              .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    private void prepareData() {
        LoginData loginData = new LoginData(roleService, userRepository, passwordEncoder);
        loginData.initializeRoles();
        userDTO = loginData.createUser();
        loginDTO = loginData.createLogin();
    }
}
