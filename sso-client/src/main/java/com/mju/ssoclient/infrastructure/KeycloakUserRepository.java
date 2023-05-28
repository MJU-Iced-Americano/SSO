package com.mju.ssoclient.infrastructure;

import com.mju.ssoclient.application.dto.UserResponse;
import com.mju.ssoclient.domain.UserRepository;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
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
            @Value("${keycloak.realm}") final String realmName,
            @Value("${mykeycloak.client.id}") final String clientId,
            @Value("${mykeycloak.admin.name}") final String adminUserName,
            @Value("${mykeycloak.admin.password}") final String adminPassword
    ) {
        this.keycloakUserRepresentationMapper = keycloakUserRepresentationMapper;

        this.keycloakUserResponseValidator = keycloakUserResponseValidator;

        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl("http://localhost:8080")
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

    @Override
    public UserResponse findById(final String userId) {
        List<UserRepresentation> list = usersResource.list();
        for (UserRepresentation userRepresentation : list) {
            log.info(userRepresentation.getId());
            if(userRepresentation.getId().contains(userId)) {
                Map<String, List<String>> attributes = userRepresentation.getAttributes();
                return new UserResponse(
                        userId,
                        userRepresentation.getUsername(),
                        userRepresentation.getEmail(),
                        attributes.get("nickname").get(0),
                        attributes.get("phoneNumber").get(0),
                        attributes.get("address").get(0),
                        attributes.get("gender").get(0),
                        attributes.get("userInformationType").get(0),
                        attributes.get("birth").get(0),
                        attributes.get("profileImageUrl").get(0)
                );
            }
        }
        throw new IllegalArgumentException("존재하지 않는 유저입니다.");
    }
}
