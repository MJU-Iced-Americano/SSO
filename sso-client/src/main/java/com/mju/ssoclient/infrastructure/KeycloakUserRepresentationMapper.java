package com.mju.ssoclient.infrastructure;

import com.mju.ssoclient.exception.UserCreateFailException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mju.domain.UserEntity;
import org.springframework.stereotype.Component;

@Component
class KeycloakUserRepresentationMapper {
    public UserRepresentation userRepresentationFrom(final UserEntity user) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(user.getUsername());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setAttributes(attributesFrom(user.getAdditionalInformation()));
        setPassword(userRepresentation, user.getPassword());
        return userRepresentation;
    }

    private void setPassword(final UserRepresentation userRepresentation, final String password) {
        CredentialRepresentation passwordCredential = new CredentialRepresentation();
        passwordCredential.setTemporary(false);
        passwordCredential.setType(CredentialRepresentation.PASSWORD);
        passwordCredential.setValue(password);
        userRepresentation.setCredentials(List.of(passwordCredential));
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
}
