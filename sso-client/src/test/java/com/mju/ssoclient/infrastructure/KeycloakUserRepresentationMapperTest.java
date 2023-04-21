package com.mju.ssoclient.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mju.domain.AdditionalInformation;
import org.mju.domain.Birth;
import org.mju.domain.Gender;
import org.mju.domain.UserEntity;

@DisplayName("Keycloak UserRepresentation 변환 관련 기능")
class KeycloakUserRepresentationMapperTest {
    private KeycloakUserRepresentationMapper keycloakUserRepresentationMapper;

    @BeforeEach
    void setUp() {
        keycloakUserRepresentationMapper = new KeycloakUserRepresentationMapper();
    }

    @DisplayName("UserEntity를 UserRepresentation으로 변환한다.")
    @Test
    void userRepresentationFrom() {
        // given
        String username = "이름";
        String email = "email@naver.com";
        String password = "a12345678!";
        String nickname = "닉네임";
        String phoneNumber = "010-1234-5678";
        String address = "주소";
        Gender gender = Gender.MALE;
        Birth birth = new Birth(LocalDate.now());

        UserEntity userEntity = new UserEntity(
                username,
                email,
                password,
                new AdditionalInformation(nickname, phoneNumber, address, gender, birth)
        );

        // when
        UserRepresentation userRepresentation = keycloakUserRepresentationMapper.userRepresentationFrom(userEntity);

        // then
        Map<String, List<String>> userRepresentationAttributes = userRepresentation.getAttributes();

        String actualPassword = userRepresentation.getCredentials()
                .stream()
                .filter(c -> c.getType().equals(CredentialRepresentation.PASSWORD))
                .findAny()
                .get()
                .getValue();

        assertAll(
                () -> assertThat(userRepresentation.getUsername()).isEqualTo(username),
                () -> assertThat(userRepresentation.getEmail()).isEqualTo(email),
                () -> assertThat(actualPassword).isEqualTo(password),
                () -> assertThat(userRepresentationAttributes.get("nickname").get(0)).isEqualTo(nickname),
                () -> assertThat(userRepresentationAttributes.get("phoneNumber").get(0)).isEqualTo(phoneNumber),
                () -> assertThat(userRepresentationAttributes.get("address").get(0)).isEqualTo(address),
                () -> assertThat(userRepresentationAttributes.get("gender").get(0)).isEqualTo(gender.toString()),
                () -> assertThat(userRepresentationAttributes.get("birth").get(0)).isEqualTo(birth.toString())
        );
    }
}
