package ru.einster.moneytransfer;

import io.micronaut.http.MediaType;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import io.restassured.RestAssured;
import io.restassured.path.json.config.JsonPathConfig;
import org.apache.http.HttpStatus;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import javax.inject.Inject;
import java.math.BigDecimal;

@MicronautTest
public class AccountAPITest extends BaseTest {


    @Test
    public void testCreateAccount() {
        Integer accountId = given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .body("{\"balance\":100.0}")
                .when().post("/accounts")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("success", equalTo(true))
                .body("payload.balance", equalTo(new BigDecimal("100.0")))
                .extract().path("payload.id");

        when().get("/accounts/" + accountId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("success", equalTo(true))
                .body("payload.id", equalTo(accountId));
    }

}
