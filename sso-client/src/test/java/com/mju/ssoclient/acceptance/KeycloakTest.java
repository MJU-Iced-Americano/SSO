package com.mju.ssoclient.acceptance;

import java.io.File;
import java.nio.file.Paths;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeEach;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class KeycloakTest {
    private static final int KEYCLOAK_SERVER_PORT = 8080;
    private static final String KEYCLOAK_SERVICE_NAME = "keycloak";
    private static final String REALM_NAME = "master";
    private static final String CLIENT_ID = "admin-cli";
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "1234";
    private static final String KEYCLOAK_SERVER_URL;
    private static final String KEYCLOAK_DOCKER_COMPOSE_FILE_PATH;

    private static final Keycloak keycloak;

    @ClassRule
    public static final DockerComposeContainer container;

    static {
        KEYCLOAK_DOCKER_COMPOSE_FILE_PATH = Paths
                .get(System.getProperty("user.dir"), "..", "keycloak-user-storage-jpa", "docker-compose-local.yml")
                .normalize()
                .toString();

        container = new DockerComposeContainer(new File(KEYCLOAK_DOCKER_COMPOSE_FILE_PATH))
                .waitingFor(KEYCLOAK_SERVICE_NAME, Wait.defaultWaitStrategy())
                .withExposedService(KEYCLOAK_SERVICE_NAME, KEYCLOAK_SERVER_PORT);

        container.start();

        KEYCLOAK_SERVER_URL = "http://" + container.getServiceHost(KEYCLOAK_SERVICE_NAME, KEYCLOAK_SERVER_PORT)
                + ":"
                + KEYCLOAK_SERVER_PORT;

        keycloak = KeycloakBuilder.builder()
                .serverUrl(KEYCLOAK_SERVER_URL)
                .grantType(OAuth2Constants.PASSWORD)
                .realm(REALM_NAME)
                .clientId(CLIENT_ID)
                .username(ADMIN_USERNAME)
                .password(ADMIN_PASSWORD)
                .build();
    }

    @BeforeEach
    void setUp() {
        cleanupUser();
    }

    private void cleanupUser() {
        UsersResource users = keycloak.realm(REALM_NAME).users();

        for (UserRepresentation userRepresentation : users.list()) {
            if (isNotAdminUser(userRepresentation)) {
                users.delete(userRepresentation.getId()).close();
            }
        }
    }

    private boolean isNotAdminUser(final UserRepresentation userRepresentation) {
        return !userRepresentation.getUsername().equals(ADMIN_USERNAME);
    }
}
