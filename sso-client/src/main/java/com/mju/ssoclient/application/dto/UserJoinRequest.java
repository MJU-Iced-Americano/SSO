package com.mju.ssoclient.application.dto;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserJoinRequest {
    private String email;
    private String password;
    private String username;
    private String nickname;
    private String phoneNumber;
    private String address;
    private String gender;
    private String userInformationType;
    private LocalDate birth;
    private String profileImageUrl;
}
