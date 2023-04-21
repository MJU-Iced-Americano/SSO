package com.mju.ssoclient.infrastructure;

import com.mju.ssoclient.domain.UserRepository;
import com.mju.ssoclient.exception.AlreadyExistUserException;
import com.mju.ssoclient.exception.UserCreateFailException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.mju.domain.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class KeycloakUserRepository implements UserRepository {
    private final UsersResource usersResource;

    public KeycloakUserRepository(
            @Value("${keycloak.server.url}") String keycloakServerUrl,
            @Value("${keycloak.realm}") String realmName,
            @Value("${keycloak.client.id}") String clientId,
            @Value("${keycloak.admin.name}") String adminUserName,
            @Value("${keycloak.admin.password}") String adminPassword
    ) {
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

        UserRepresentation userRepresentation = userRepresentationFrom(user);

        synchronized (usersResource) {
            Response response = usersResource.create(userRepresentation);
            validationUserCreate(response);
        }

        return usersResource.search(userRepresentation.getUsername()).stream()
                .filter(u -> u.getUsername().equals(userRepresentation.getUsername()))
                .findAny()
                .orElseThrow(UserCreateFailException::new)
                .getId();
    }

    private UserRepresentation userRepresentationFrom(final UserEntity user) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(user.getUsername());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setAttributes(attributesFrom(user.getAdditionalInformation()));
        return userRepresentation;
    }

    private Map<String, List<String>> attributesFrom(final Object object) {
        Map<String, List<String>> attributes = new HashMap<>();
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                attributes.put(field.getName(), List.of(field.get(object).toString()));
            } catch (IllegalAccessException e) {
                throw new UserCreateFailException();
            }
            field.setAccessible(false);
        }
        return attributes;
    }

    private void validationUserCreate(final Response response) {
        if (response.getStatus() == HttpStatus.CONFLICT.value()) {
            throw new AlreadyExistUserException();
        }
        if (response.getStatus() != HttpStatus.CREATED.value()) {
            throw new UserCreateFailException();
        }
    }
}
