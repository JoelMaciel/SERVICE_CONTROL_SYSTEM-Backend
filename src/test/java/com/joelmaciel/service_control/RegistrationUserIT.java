package com.joelmaciel.service_control;

import com.joelmaciel.service_control.api.dto.UserDTO;
import com.joelmaciel.service_control.api.security.dto.LoginDTO;
import com.joelmaciel.service_control.domain.repository.UserRepository;
import com.joelmaciel.service_control.domain.service.RoleService;
import com.joelmaciel.service_control.util.DatabaseCleaner;
import com.joelmaciel.service_control.util.LoginData;
import com.joelmaciel.service_control.util.ResourceUtils;
import com.joelmaciel.service_control.util.TokenUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
public class RegistrationUserIT {

    public static final long USER_ID = 1L;
    public static final long USER_ID_ROLE_USER = 2L;

    public static final long USER_NOT_EXISTS = 3L;
    public static final String REQUIRED_FIELDS = "One or more fields are invalid. Fill in the correct form and try again.";
    public static final String USER_NOT_FOUND = "Resource Not Found";
    @LocalServerPort
    private int port;
    private String token;
    private UserDTO userDTOAdmin;
    private UserDTO userDTORoleUser;
    private LoginDTO loginDTO;
    private String jsonLoginCorrect;
    private String jsonUpdateUserSuccessfully;
    private String jsonUserUpdateNoUsername;

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

         jsonUpdateUserSuccessfully = ResourceUtils.getContentFromResource(
                "/json/correct/user-update-correct.json");

        jsonUserUpdateNoUsername = ResourceUtils.getContentFromResource(
                "/json/incorrect/user-incorrect-update-no-username-correct.json"
        ) ;



        token = TokenUtils.getToken(port, jsonLoginCorrect);
        databaseCleaner.clearTables();
        prepareData();
    }

    @Test
    public void shouldReturn200_WhenGetAllUsers() {
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
              .when()
                .get()
              .then()
                .statusCode(HttpStatus.OK.value());
    }
    @Test
    public void shouldReturn200_WhenFindById() {
        given()
                .pathParams("userId", USER_ID)
                .accept(ContentType.JSON).header("Authorization", "Bearer " + token)
              .when()
                .get("/{userId}")
              .then()
                .statusCode(HttpStatus.OK.value());

    }
    @Test
    public void shouldReturn404_WhenIdDoesNotExist() {
        given()
                .pathParams("userId", USER_NOT_EXISTS)
                .accept(ContentType.JSON).header("Authorization", "Bearer " + token)
              .when()
                .get("/{userId}")
              .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("title", equalTo(USER_NOT_FOUND));

    }

    @Test
    public void shouldReturn200_WhenUpdateUserSuccessfully() {
        given()
                .pathParams("userId", USER_ID_ROLE_USER)
                .body(jsonUpdateUserSuccessfully)
                .accept(ContentType.JSON).header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
              .when()
                .put("/{userId}")
              .then()
                .statusCode(HttpStatus.OK.value())
                .body("username", equalTo("vianamaciel31"))
                .body("email", equalTo("jmviana@gmail.com"));


    }
 @Test
    public void shouldReturn404_WhenUpdateIdDoesNotExist() {
        given()
                .pathParams("userId", USER_NOT_EXISTS)
                .body(jsonUpdateUserSuccessfully)
                .accept(ContentType.JSON).header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
              .when()
                .put("/{userId}")
              .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("title", equalTo(USER_NOT_FOUND));


    }
    @Test
    public void shouldReturn400_WhenUpdateUsernameDoesNotExist() {
        given()
                .pathParams("userId", USER_ID_ROLE_USER)
                .body(jsonUserUpdateNoUsername)
                .accept(ContentType.JSON).header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
              .when()
                .put("/{userId}")
              .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("detail", equalTo(REQUIRED_FIELDS));


    }

    @Test
    public void shouldReturn204_WhenDeleteUserSuccessfully() {
        given()
                .pathParams("userId", USER_ID_ROLE_USER)
                .accept(ContentType.JSON).header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .when()
                .delete("/{userId}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

    }
    @Test
    public void shouldReturn404_WhenDeleteUserNotExists() {
        given()
                .pathParams("userId", USER_NOT_EXISTS)
                .accept(ContentType.JSON).header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .when()
                .delete("/{userId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("title", equalTo(USER_NOT_FOUND));

    }

    private void prepareData() {
        LoginData loginData = new LoginData(roleService, userRepository, passwordEncoder);
        loginData.initializeRoles();
        userDTOAdmin = loginData.createUserAdmin();
        userDTORoleUser = loginData.createUserRoleUser();
        loginDTO = loginData.createLogin();
    }
}
