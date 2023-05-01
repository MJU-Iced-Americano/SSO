package com.mju.ssoclient.acceptance;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;

public class UserAcceptanceSteps {
    public static ExtractableResponse<Response> 회원가입_요청(
            final String email,
            final String password,
            final String username,
            final String nickname,
            final String phoneNumber,
            final String address,
            final String gender,
            final String birth,
            final String profileImageUrl
    ) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        params.put("username", username);
        params.put("nickname", nickname);
        params.put("phoneNumber", phoneNumber);
        params.put("address", address);
        params.put("gender", gender);
        params.put("birth", birth);
        params.put("profileImageUrl", profileImageUrl);

        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when()
                .post("/user/join")
                .then().log().all()
                .extract();
    }
}
