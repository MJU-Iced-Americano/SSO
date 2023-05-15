package com.mju.ssoclient.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.Response;
import org.apache.http.HttpStatus;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.IdentityProvidersResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.IdentityProviderRepresentation;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfiguration implements InitializingBean {
    @Value("${keycloak.realm}")
    private String realmName;
    @Value("${mykeycloak.client.id}")
    private String clientId;
    @Value("${mykeycloak.admin.name}")
    private String adminUserName;
    @Value("${mykeycloak.admin.password}")
    private String adminPassword;
    @Value("${keycloak.resource}")
    private String adminClientId;
    @Value("${keycloak.credentials.secret}")
    private String adminClientSecret;

    @Override
    public void afterPropertiesSet() {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl("http://localhost:8080")
                .grantType(OAuth2Constants.PASSWORD)
                .realm(realmName)
                .clientId(clientId)
                .username(adminUserName)
                .password(adminPassword)
                .build();

        if (!isAlreadyCreateAdminClient(keycloak)) {
            createAdminClient(keycloak);
        }

        RealmResource realm = keycloak.realm(realmName);
        IdentityProvidersResource identityProvidersResource = realm.identityProviders();
        identityProvidersResource.create(createIdentityProvider("google"));
        identityProvidersResource.create(createIdentityProvider("github"));
    }

    private Boolean isAlreadyCreateAdminClient(final Keycloak keycloak) {
        return keycloak.realms()
                .realm(realmName)
                .clients()
                .findAll()
                .stream()
                .anyMatch(c -> c.getClientId().equals(adminClientId));
    }

    private void createAdminClient(final Keycloak keycloak) {
        ClientRepresentation clientRepresentation = new ClientRepresentation();
        clientRepresentation.setClientId(adminClientId);
        clientRepresentation.setSecret(adminClientSecret);
        clientRepresentation.setRedirectUris(List.of(
                "http://localhost/auth",
                "http://localhost:8080/realms/master/broker/google/endpoint",
                "http://localhost:8080/realms/master/broker/github/endpoint"
        ));
        clientRepresentation.setWebOrigins(List.of("http://localhost:80/*"));
        clientRepresentation.setRootUrl("http://localhost/auth");

        Response response = keycloak.realm(realmName).clients().create(clientRepresentation);

        if (response.getStatus() != HttpStatus.SC_CREATED) {
            throw new RuntimeException("Client 생성에 실패했습니다.");
        }
    }

    private IdentityProviderRepresentation createIdentityProvider(String alias) {
        IdentityProviderRepresentation identityProviderRepresentation = new IdentityProviderRepresentation();
        identityProviderRepresentation.setAlias(alias);
        identityProviderRepresentation.setProviderId(alias);
        identityProviderRepresentation.setEnabled(true);
        Map<String, String> config = new HashMap<>();
        config.put("clientId", adminClientId);
        config.put("clientSecret", adminClientSecret);
        identityProviderRepresentation.setConfig(config);
        return identityProviderRepresentation;
    }
}
