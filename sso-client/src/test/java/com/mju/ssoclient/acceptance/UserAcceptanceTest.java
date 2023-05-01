package com.mju.ssoclient.acceptance;

import static com.mju.ssoclient.acceptance.UserAcceptanceSteps.회원가입_요청;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("유저 관련 기능")
public class UserAcceptanceTest extends AcceptanceTest {
    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
    }

    @DisplayName("회원가입 관련 기능")
    @Nested
    class JoinTest {
        /**
         * When 회원가입을 요청하면
         * Then 회원가입에 성공한다
         */
        @DisplayName("회원가입을 요청하면 회원가입에 성공한다.")
        @Test
        void join() {
            // when
            var response = 회원가입_요청(
                    "test@naver.com",
                    "ABC12345678!",
                    "test",
                    "nick",
                    "010-0000-0000",
                    "서울특별시 서대문구",
                    "MALE",
                    "2000-01-01",
                    "https://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg"
            );

            // then
            Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        }

        /**
         * When 회원가입시 입력한 이메일이 이미 회원가입된 이메일인 경우
         * Then 회원가입에 실패한다
         */
        @DisplayName("회원가입시 입력한 이메일이 이미 회원가입된 이메일인 경우 회원가입에 실패한다.")
        @Test
        void joinEmailDuplicate() {
            // given
            String email = "test@naver.com";

            회원가입_요청(email, "ABC12345678!", "test", "nick", "010-0000-0000", "서울특별시 서대문구", "MALE", "2000-01-01",
                    "https://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg");
            // when
            var response = 회원가입_요청(
                    email,
                    "new12345678!",
                    "newName",
                    "newNick",
                    "010-1234-5678",
                    "서울특별시 마포구",
                    "MALE",
                    "2001-01-01",
                    "https://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg"
            );

            // then
            Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.CONFLICT.value());
        }
    }
}
