package com.mju.ssoclient.infrastructure;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.mju.ssoclient.exception.AlreadyExistUserException;
import com.mju.ssoclient.exception.UserCreateFailException;
import java.util.stream.Stream;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;

@DisplayName("keycloak 유저 검증 관련 기능")
class KeycloakUserResponseValidatorTest {
    private KeycloakUserResponseValidator keycloakUserResponseValidator;

    @BeforeEach
    void setUp() {
        keycloakUserResponseValidator = new KeycloakUserResponseValidator();
    }

    @DisplayName("Response 응답 상태가 CREATED 이면 예외를 반환하지 않는다.")
    @Test
    void validationUserCreateResponseIsCreated() {
        assertDoesNotThrow(
                () -> keycloakUserResponseValidator
                        .validationUserCreateResponse(Response.status(Status.CREATED).build())
        );
    }

    @DisplayName("Response 응답 상태가 CONFLICT 이면 AlreadyExistsUserException 예외를 반환한다.")
    @Test
    void validationUserCreateResponseIsConflict() {
        assertThatThrownBy(
                () -> keycloakUserResponseValidator
                        .validationUserCreateResponse(Response.status(Status.CONFLICT).build())
        ).isInstanceOf(AlreadyExistUserException.class);
    }

    @DisplayName("Response 응답 상태가 CREATED와 CONFLICT가 아니면 UserCreateFailException 예외를 반환한다.")
    @MethodSource("provideValidationUserCreateResponseIsConflict")
    @ParameterizedTest(name = "status : {0}")
    void validationUserCreateResponseIsConflict(final HttpStatus status) {
        assertThatThrownBy(
                () -> keycloakUserResponseValidator
                        .validationUserCreateResponse(Response.status(status.value()).build())
        ).isInstanceOf(UserCreateFailException.class);
    }

    public static Stream<Arguments> provideValidationUserCreateResponseIsConflict() {
        return Stream.of(
                Arguments.of(HttpStatus.OK),
                Arguments.of(HttpStatus.BAD_REQUEST),
                Arguments.of(HttpStatus.INTERNAL_SERVER_ERROR),
                Arguments.of(HttpStatus.REQUEST_TIMEOUT),
                Arguments.of(HttpStatus.UNAUTHORIZED),
                Arguments.of(HttpStatus.FORBIDDEN),
                Arguments.of(HttpStatus.REQUEST_TIMEOUT)
        );
    }
}
