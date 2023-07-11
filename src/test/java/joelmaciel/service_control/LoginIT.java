package joelmaciel.service_control;


import io.restassured.RestAssured;
import joelmaciel.service_control.api.dto.UserDTO;
import joelmaciel.service_control.api.security.dto.JwtDTO;
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
import static org.hamcrest.CoreMatchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class LoginIT {

    private static final String INVALID_DATA = "Incorrect username or password.";
    private static final String INVALID_FIELDS = "One or more fields are invalid. Fill in the correct form and try again.";
    @LocalServerPort
    private int port;

    private String token;
    private String jsonLoginCorrect;
    private String jsonIncorrectUsername;

    private String jsonIncorrectPassword;

    private String jsonWithoutUsernameAndCpf;

    private UserDTO userDTO;
    private LoginDTO loginDTO;
    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/api";

        jsonLoginCorrect = ResourceUtils.getContentFromResource(
                "/json/correct/login-dto.json"
        );

        jsonIncorrectUsername = ResourceUtils.getContentFromResource(
                "/json/incorrect/incorrect-username-login-dto.json"
        );
        jsonIncorrectPassword = ResourceUtils.getContentFromResource(
                "/json/incorrect/incorrect-password-login-dto.json"
        );

        jsonWithoutUsernameAndCpf = ResourceUtils.getContentFromResource(
                "/json/incorrect/without-username-and-cpf-password-login-dto.json"
        );

        token = TokenUtils.getToken(port, jsonLoginCorrect);
        databaseCleaner.clearTables();
        prepareData();

    }

    @Test
    public void shouldReturn201_WhenCorrectUsernameAndPassword() {

            given()
                .contentType("application/json")
                .body(jsonLoginCorrect)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("type", equalTo("Bearer"))
                .extract()
                .as(JwtDTO.class);

    }

    @Test
    public void shouldReturn400_WhenIncorrectUsername() {
        given()
                .contentType("application/json")
                .body(jsonIncorrectUsername)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("userMessage", equalTo(INVALID_DATA));

    }
    @Test
    public void shouldReturn400_WhenIncorrectPassword() {
        given()
                .contentType("application/json")
                .body(jsonIncorrectPassword)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("userMessage", equalTo(INVALID_DATA));

    }

    @Test
    public void shouldReturn400_WithoutUsernameAndCpf() {
        given()
                .contentType("application/json")
                .body(jsonWithoutUsernameAndCpf)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("userMessage", equalTo(INVALID_FIELDS));

    }

    private void prepareData() {

        LoginData loginData = new LoginData(roleService, userRepository, passwordEncoder);
        loginData.initializeRoles();
        userDTO = loginData.createUser();
        loginDTO = loginData.createLogin();

    }

}
