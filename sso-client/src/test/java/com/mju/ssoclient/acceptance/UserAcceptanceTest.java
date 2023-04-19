package com.mju.ssoclient.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("유저 관련 기능")
public class UserAcceptanceTest extends AcceptanceTest {

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

        }

        /**
         * When 회원가입시 입력한 이메일이 이미 회원가입된 이메일인 경우
         * Then 회원가입에 실패한다
         */
        @DisplayName("회원가입시 입력한 이메일이 이미 회원가입된 이메일인 경우 회원가입에 실패한다.")
        @Test
        void joinEmailDuplicate() {

        }
    }
}
