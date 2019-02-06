package ru.einster.moneytransfer;

import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import io.restassured.RestAssured;
import io.restassured.path.json.config.JsonPathConfig;
import org.junit.jupiter.api.BeforeEach;

import javax.inject.Inject;

import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.config.RestAssuredConfig.newConfig;

public class BaseTest {

    @Inject
    protected EmbeddedServer server;


    @BeforeEach
    public void init() {
        RestAssured.baseURI = "http://" + server.getHost() + ":" + server.getPort();
        RestAssured.config =
                newConfig().jsonConfig(jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL));

    }
}
