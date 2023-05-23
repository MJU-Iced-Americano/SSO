package com.mju.ssoclient.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultResponse<T> {

    // API 상태 코드
    private Integer statusCode;

    // API 부가 설명
    private String message;

    // API 응답 데이터
    private T data;

    // 상태 코드 + 부가 설명 반환
    public static <T> DefaultResponse<T> response(final Integer statusCode, final String message) {
        return new DefaultResponse<>(statusCode, message);
    }

    // 상태 코드 + 부가 설명 + 응답 데이터 반환
    public static <T> DefaultResponse<T> response(final Integer statusCode, final String message, final T data) {
        return new DefaultResponse<>(statusCode, message, data);
    }

    private DefaultResponse(final Integer statusCode, final String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
