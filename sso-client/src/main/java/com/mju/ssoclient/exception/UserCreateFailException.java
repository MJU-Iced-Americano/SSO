package com.mju.ssoclient.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UserCreateFailException extends RuntimeException {
    private static final String MESSAGE = "회원 생성에 실패했습니다.";

    public UserCreateFailException() {
        super(MESSAGE);
    }
}
