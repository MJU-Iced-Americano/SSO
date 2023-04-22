package com.mju.ssoclient.infrastructure;

import com.mju.ssoclient.domain.UserRepository;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.mju.domain.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
class KeycloakUserRepository implements UserRepository {
    private final UsersResource usersResource;
    private final KeycloakUserRepresentationMapper keycloakUserRepresentationMapper;
    private final KeycloakUserResponseValidator keycloakUserResponseValidator;

    private KeycloakUserRepository(
            final KeycloakUserRepresentationMapper keycloakUserRepresentationMapper,
            final KeycloakUserResponseValidator keycloakUserResponseValidator,
            @Value("${keycloak.server.url}") final String keycloakServerUrl,
            @Value("${keycloak.realm}") final String realmName,
            @Value("${keycloak.client.id}") final String clientId,
            @Value("${keycloak.admin.name}") final String adminUserName,
            @Value("${keycloak.admin.password}") final String adminPassword
    ) {
        this.keycloakUserRepresentationMapper = keycloakUserRepresentationMapper;

        this.keycloakUserResponseValidator = keycloakUserResponseValidator;

        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakServerUrl)
                .grantType(OAuth2Constants.PASSWORD)
                .realm(realmName)
                .clientId(clientId)
                .username(adminUserName)
                .password(adminPassword)
                .build();

        usersResource = keycloak.realm(realmName).users();
    }

    @Override
    public String join(final UserEntity user) {
        log.info("join username : {}, email : {}", user.getUsername(), user.getEmail());

        UserRepresentation userRepresentation = keycloakUserRepresentationMapper.userRepresentationFrom(user);

        Response userCreateResponse = usersResource.create(userRepresentation);

        keycloakUserResponseValidator.validationUserCreateResponse(userCreateResponse);

        String createUserUsername = userRepresentation.getUsername();
        return keycloakUserRepresentationMapper
                .userIdByEqualUsernameFrom(usersResource.search(createUserUsername), createUserUsername);
    }
}
