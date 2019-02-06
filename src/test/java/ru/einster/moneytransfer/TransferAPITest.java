package ru.einster.moneytransfer;

import io.micronaut.http.MediaType;
import io.micronaut.test.annotation.MicronautTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@MicronautTest
public class TransferAPITest extends BaseTest {


    @Test
    public void testSuccessTransfer() {
        BigDecimal fromAccountBalance = new BigDecimal("200.0");
        BigDecimal toAccountBalance = new BigDecimal("50.0");
        BigDecimal transferAmount = BigDecimal.ONE;
        Long fromAccountId = createAccount(fromAccountBalance);
        Long toAccountId = createAccount(toAccountBalance);

        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .body(generateTransferBody(fromAccountId, toAccountId, transferAmount))
                .when().post("/transfers")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("success", equalTo(true));

        Assertions.assertEquals(0, getAccountBalance(fromAccountId)
                .compareTo(fromAccountBalance.add(transferAmount.negate())));
        Assertions.assertEquals(0, getAccountBalance(toAccountId)
                .compareTo(toAccountBalance.add(transferAmount)));
    }

    @Test
    public void testSameAccount() {
        BigDecimal balance = BigDecimal.ONE;
        Long accountId = createAccount(balance);
        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .body(generateTransferBody(accountId, accountId, BigDecimal.ONE))
                .when().post("/transfers")
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body("success", equalTo(false));
        Assertions.assertEquals(0, getAccountBalance(accountId).compareTo(balance));
    }

    public void testIncorrectBalance() {
        BigDecimal fromBalance = new BigDecimal("100.0");
        BigDecimal toBalance = BigDecimal.ONE;
        BigDecimal transferAmount = fromBalance.add(fromBalance);

        Long fromAccountId = createAccount(fromBalance);
        Long toAccountId = createAccount(toBalance);
        given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .body(generateTransferBody(fromAccountId, toAccountId, transferAmount))
                .when().post("/transfers")
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);

        Assertions.assertEquals(0, getAccountBalance(fromAccountId).compareTo(fromBalance));
        Assertions.assertEquals(0, getAccountBalance(toAccountId).compareTo(toBalance));
    }

    private String generateTransferBody(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        return "{\"fromAccountId\":" + fromAccountId + ",\n" +
                "\"toAccountId\": " + toAccountId + ", " +
                "\"amount\":" + amount + "}";
    }

    private Long createAccount(BigDecimal balance) {
        Integer accountId = given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .body("{\"balance\":" + balance + "}")
                .when().post("/accounts")
                .then()
                .extract().path("payload.id");
        return Long.valueOf(accountId);
    }

    private BigDecimal getAccountBalance(Long id) {
        return given()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .when().get("/accounts/" + id)
                .then()
                .extract().path("payload.balance");
    }
}
