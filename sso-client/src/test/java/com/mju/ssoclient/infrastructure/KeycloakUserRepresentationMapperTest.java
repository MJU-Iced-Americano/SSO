package com.mju.ssoclient.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.mju.ssoclient.exception.UserCreateFailException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
        UserEntity userEntity = new UserEntity(
                "이름",
                "email@naver.com",
                "a12345678!",
                new AdditionalInformation("닉네임", "010-1234-5678", "주소", Gender.MALE, new Birth(LocalDate.now()),
                        "https://www.google.com/search?q=%EC%9D%B4%EB%AF%B8%EC%A7%80&rlz=1C5CHFA_enKR1023KR1023&sxsrf=APwXEdeoS5NHNytMzZfiSi2uDQ2Io0_dOg:1682939446544&tbm=isch&source=iu&ictx=1&vet=1&fir=1VQz4qfBZ3knDM%252CyqSrMQ_lLcRCtM%252C%252Fm%252F0dj30p&usg=AI4_-kR4UZhMYOOcHfG_ozWOE8Eju1_KJw&sa=X&ved=2ahUKEwjK1I7__dP-AhU9h1YBHYuQAmEQ_B16BAhFEAI#imgrc=1VQz4qfBZ3knDM")
        );

        // when
        UserRepresentation userRepresentation = keycloakUserRepresentationMapper.userRepresentationFrom(userEntity);

        // then
        Map<String, List<String>> userRepresentationAttributes = userRepresentation.getAttributes();
        AdditionalInformation userEntityAdditionalInformation = userEntity.getAdditionalInformation();
        String actualPassword = userRepresentation.getCredentials()
                .stream()
                .filter(c -> c.getType().equals(CredentialRepresentation.PASSWORD))
                .findAny()
                .get()
                .getValue();

        assertAll(
                () -> assertThat(userRepresentation.getUsername())
                        .isEqualTo(userEntity.getUsername()),
                () -> assertThat(userRepresentation.getEmail())
                        .isEqualTo(userEntity.getEmail()),
                () -> assertThat(actualPassword)
                        .isEqualTo(userEntity.getPassword()),
                () -> assertThat(userRepresentationAttributes.get("nickname").get(0))
                        .isEqualTo(userEntityAdditionalInformation.getNickname()),
                () -> assertThat(userRepresentationAttributes.get("phoneNumber").get(0))
                        .isEqualTo(userEntityAdditionalInformation.getPhoneNumber()),
                () -> assertThat(userRepresentationAttributes.get("address").get(0))
                        .isEqualTo(userEntityAdditionalInformation.getAddress()),
                () -> assertThat(userRepresentationAttributes.get("gender").get(0))
                        .isEqualTo(userEntityAdditionalInformation.getGender().toString()),
                () -> assertThat(userRepresentationAttributes.get("birth").get(0))
                        .isEqualTo(userEntityAdditionalInformation.getBirth().toString()),
                () -> assertThat(userRepresentationAttributes.get("profileImageUrl").get(0))
                        .isEqualTo(userEntityAdditionalInformation.getProfileImageUrl())
        );
    }

    @DisplayName("유저 목록 중 이름이 일치하는 유저의 ID를 반환한다.")
    @Test
    void userIdByEqualUsernameFrom() {
        // given
        String userId = UUID.randomUUID().toString();
        String username = "equalUsername";
        UserRepresentation userThatEqualToUsername = new UserRepresentation();
        userThatEqualToUsername.setUsername(username);
        userThatEqualToUsername.setId(userId);

        UserRepresentation userThatNotEqualToUsername = new UserRepresentation();
        userThatNotEqualToUsername.setUsername("notEqualUsername");
        userThatNotEqualToUsername.setUsername(UUID.randomUUID().toString());

        List<UserRepresentation> userRepresentations = List.of(userThatNotEqualToUsername, userThatEqualToUsername);

        // when & then
        assertThat(keycloakUserRepresentationMapper.userIdByEqualUsernameFrom(userRepresentations, username))
                .isEqualTo(userId);
    }

    @DisplayName("유저 목록 중 이름이 일치하는 유저가 없는 경우 예외가 발생한다.")
    @Test
    void userIdByEqualUsernameFromUsersNotContainUsernameEqualUser() {
        // given
        String userId = UUID.randomUUID().toString();
        UserRepresentation userThatEqualToUsername = new UserRepresentation();
        userThatEqualToUsername.setUsername("equalUsername");
        userThatEqualToUsername.setId(userId);

        List<UserRepresentation> userRepresentations = List.of(userThatEqualToUsername);

        // when & then
        assertThatThrownBy(
                () -> keycloakUserRepresentationMapper.userIdByEqualUsernameFrom(userRepresentations, "존재하지 않는 이름")
        ).isInstanceOf(UserCreateFailException.class);
    }
}
