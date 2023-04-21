package com.mju.ssoclient.infrastructure;

import com.mju.ssoclient.exception.AlreadyExistUser;
import com.mju.ssoclient.exception.UserCreateFailException;
import javax.ws.rs.core.Response;
import org.springframework.http.HttpStatus;

class KeycloakUserResponseValidator {
    protected void validationUserCreateResponse(final Response response) {
        if (response.getStatus() == HttpStatus.CONFLICT.value()) {
            throw new AlreadyExistUser();
        }
        if (response.getStatus() != HttpStatus.CREATED.value()) {
            throw new UserCreateFailException();
        }
    }
}
