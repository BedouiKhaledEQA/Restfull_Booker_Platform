package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class loginAPI_test {

    @Test(groups = {"API"})
    @Parameters({"baseURI"})
    public void loginTest(String baseURI) {

        RestAssured.baseURI = baseURI;

        String requestBody = "{ \"username\": \"admin\", \"password\": \"password\" }";

        Response response = RestAssured
                .given()
                .log().all()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/auth/login")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        // Récupération du token depuis le cookie
        String token = response.getCookie("token");
        System.out.println("Token: " + token);

        // Vérifie que le token n'est pas null ou vide
        Assert.assertNotNull(token, "Le token ne doit pas être null");
        Assert.assertFalse(token.isEmpty(), "Le token ne doit pas être vide");
    }
}