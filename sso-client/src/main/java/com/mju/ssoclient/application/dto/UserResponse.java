package com.mju.ssoclient.application.dto;

import lombok.Getter;
import org.mju.domain.AdditionalInformation;
import org.mju.domain.Birth;
import org.mju.domain.Gender;
import org.mju.domain.UserEntity;
import org.mju.domain.UserInformationType;

@Getter
public class UserResponse {
    private String id;

    private String username;

    private String email;

    private String nickname;

    private String phoneNumber;

    private String address;

    private String gender;

    private String userInformationType;

    private String birth;

    private String profileImageUrl;

    public UserResponse(final String id, final String username, final String email, final String nickname,
                        final String phoneNumber, final String address,
                        final String gender, final String userInformationType, final String birth,
                        final String profileImageUrl) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
        this.userInformationType = userInformationType;
        this.birth = birth;
        this.profileImageUrl = profileImageUrl;
    }
}
