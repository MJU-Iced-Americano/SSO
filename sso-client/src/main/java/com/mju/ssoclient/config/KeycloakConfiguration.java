package com.mju.ssoclient.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private String githubClient;
    @Value("${spring.security.oauth2.client.registration.github.client-secret}")
    private String githubSecret;
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClient;
    @Value("${spring.security.oauth2.client.registration.github.redirect-uri}")
    private String githubRedirectURI;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleSecret;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectURI;
    @Value("${keycloak.server-url}")
    private String keycloakServerURL;
    @Value("${keycloak.client-url}")
    private String clientServerURL;

    @Override
    public void afterPropertiesSet() {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakServerURL)
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
        identityProvidersResource
                .create(createIdentityProvider("github", githubClient, githubSecret));
        identityProvidersResource
                .create(createIdentityProvider("google", googleClient, googleSecret));
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
                clientServerURL + "/auth",
                googleRedirectURI,
                githubRedirectURI
        ));
        clientRepresentation.setWebOrigins(List.of(clientServerURL + "/*"));
        clientRepresentation.setRootUrl(clientServerURL + "/auth");

        Response response = keycloak.realm(realmName).clients().create(clientRepresentation);

        if (response.getStatus() != HttpStatus.SC_CREATED) {
            throw new RuntimeException("Client 생성에 실패했습니다.");
        }
    }

    private IdentityProviderRepresentation createIdentityProvider(
            final String alias,
            final String clientId,
            final String clientSecret
    ) {
        IdentityProviderRepresentation identityProviderRepresentation = new IdentityProviderRepresentation();
        identityProviderRepresentation.setAlias(alias);
        identityProviderRepresentation.setProviderId(alias);
        identityProviderRepresentation.setEnabled(true);
        Map<String, String> config = new HashMap<>();
        config.put("clientId", clientId);
        config.put("clientSecret", clientSecret);
        identityProviderRepresentation.setConfig(config);
        return identityProviderRepresentation;
    }
}
