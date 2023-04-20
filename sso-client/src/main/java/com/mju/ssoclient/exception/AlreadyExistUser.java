package com.mju.ssoclient.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExistUser extends RuntimeException {
    private static final String MESSAGE = "이미 존재하는 유저입니다.";

    public AlreadyExistUser() {
        super(MESSAGE);
    }
}
