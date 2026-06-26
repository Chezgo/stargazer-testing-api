package api.keycloak;

import config.TestConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class KeycloakAuth {

    public String getAdminAccessToken() {
        return getToken(
                TestConfig.getAdminUsername(),
                TestConfig.getAdminPassword()
        );
    }

    public String getUserAccessToken() {
        return getToken(
                TestConfig.getUserUsername(),
                TestConfig.getUserPassword()
        );
    }

    private String getToken(String username, String password) {
        Response response = RestAssured.given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("grant_type", "password")
                .formParam("client_id", TestConfig.getKeycloakClientId())
                .formParam("username", username)
                .formParam("password", password)
                .when()
                .post(TestConfig.getKeycloakUrl());

        response.then().statusCode(200);
        return response.jsonPath().getString("access_token");
    }
}
