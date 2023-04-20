package com.mju.ssoclient.acceptance;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AcceptanceTest extends KeycloakTest {
    @LocalServerPort
    private int port;

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        RestAssured.port = port;
    }
}
