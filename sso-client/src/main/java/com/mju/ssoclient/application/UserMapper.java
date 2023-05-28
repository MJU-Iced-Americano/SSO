package com.mju.ssoclient.application;

import com.mju.ssoclient.application.dto.UserJoinRequest;
import org.mju.domain.AdditionalInformation;
import org.mju.domain.Birth;
import org.mju.domain.Gender;
import org.mju.domain.UserEntity;
import org.mju.domain.UserInformationType;
import org.springframework.stereotype.Component;

@Component
class UserMapper {
    public UserEntity userFrom(final UserJoinRequest userJoinRequest) {
        AdditionalInformation additionalInformation = new AdditionalInformation(
                userJoinRequest.getNickname(),
                userJoinRequest.getPhoneNumber(),
                userJoinRequest.getAddress(),
                Gender.valueOf(userJoinRequest.getGender()),
                UserInformationType.valueOf(userJoinRequest.getUserInformationType()),
                new Birth(userJoinRequest.getBirth()),
                userJoinRequest.getProfileImageUrl()
        );

        return new UserEntity(
                userJoinRequest.getUsername(),
                userJoinRequest.getEmail(),
                userJoinRequest.getPassword(),
                additionalInformation
        );
    }
}
