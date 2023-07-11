package joelmaciel.service_control.util;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import joelmaciel.service_control.api.security.dto.JwtDTO;
import org.springframework.http.HttpStatus;


public class TokenUtils {
    public static String getToken(int port, String jsonLoginCorrect) {
        String authEndpoint = "http://localhost:" + port + "/api/auth/login";

        Response response = RestAssured.given()
                .contentType("application/json")
                .body(jsonLoginCorrect)
                .post(authEndpoint);

        if (response.getStatusCode() != HttpStatus.CREATED.value()) {
            throw new RuntimeException("Failed to authenticate. Status code: " + response.getStatusCode());
        }

        JwtDTO jwtDTO = response.as(JwtDTO.class);
        String token = jwtDTO.getToken();

        if (token == null) {
            throw new RuntimeException("Token not found in the authentication response.");
        }

        return token;
    }
}



































