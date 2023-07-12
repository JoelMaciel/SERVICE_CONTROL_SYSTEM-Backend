package joelmaciel.service_control;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
public class AuthenticationUserIT {

    private static final String INVALID_DATA = "Incorrect username or password.";
    private static final String INVALID_SAVE_DATA = "Invalid Data";
    private static final String INVALID_FIELDS = "One or more fields are invalid. Fill in the correct form and try again.";
    @LocalServerPort
    private int port;

    private String token;
    private String jsonLoginCorrect;
    private String jsonUserCorrect;
    private String jsonNoUsername;
    private String jsonUserCpfInvalid;
    private String jsonUserEmailInvalid;
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

        jsonUserCorrect = ResourceUtils.getContentFromResource(
                "/json/correct/user-correct.json");

        jsonNoUsername = ResourceUtils.getContentFromResource(
                "/json/incorrect/no-username.json"
        ) ;

        jsonUserCpfInvalid = ResourceUtils.getContentFromResource(
                "/json/incorrect/user-with-invalid-cpf.json"
        );

        jsonUserEmailInvalid = ResourceUtils.getContentFromResource(
                "/json/incorrect/user-with-invalid-email.json"
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
    public void shouldReturn201_WhenLoginCorrectUsernameAndPassword() {

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
    public void shouldReturn400_WhenLoginIncorrectPassword() {
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
    public void shouldReturn400_WhenLoginWithoutUsernameAndCpf() {
        given()
                .contentType("application/json")
                .body(jsonWithoutUsernameAndCpf)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("userMessage", equalTo(INVALID_FIELDS));

    }

    @Test
    public void shouldReturn201_WhenSaveUserSuccessfully() {
        given()
                .body(jsonUserCorrect)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
              .when()
                .post("/auth/signup")
              .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void shouldReturn400_WhenSaveUserWithoutUsername() {
        given()
                .body(jsonNoUsername)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
              .when()
                .post("/auth/signup")
              .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void shouldReturn400_WhenLoginWithIncorrectUsername() {
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
    public void shouldReturn400_WhenSaveUserWithCpfInvalid() {
        given()
                .contentType("application/json")
                .body(jsonUserCpfInvalid)
              .when()
                .post("/auth/signup")
              .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(INVALID_SAVE_DATA));

    }

    @Test
    public void shouldReturn400_WhenSaveUserWithEmailInvalid() {
        given()
                .contentType("application/json")
                .body(jsonUserEmailInvalid)
                .when()
                .post("/auth/signup")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(INVALID_SAVE_DATA));

    }


    private void prepareData() {

        LoginData loginData = new LoginData(roleService, userRepository, passwordEncoder);
        loginData.initializeRoles();
        userDTO = loginData.createUser();
        loginDTO = loginData.createLogin();

    }

}
