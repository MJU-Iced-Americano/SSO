package com.mju.ssoclient.application;

import com.mju.ssoclient.application.dto.UserJoinRequest;
import org.mju.domain.AdditionalInformation;
import org.mju.domain.Birth;
import org.mju.domain.Gender;
import org.mju.domain.UserEntity;
import org.springframework.stereotype.Component;

@Component
class UserMapper {
    public UserEntity userFrom(UserJoinRequest userJoinRequest) {
        AdditionalInformation additionalInformation = new AdditionalInformation(
                userJoinRequest.getNickname(),
                userJoinRequest.getPhoneNumber(),
                userJoinRequest.getAddress(),
                Gender.valueOf(userJoinRequest.getGender()),
                new Birth(userJoinRequest.getBirth())
        );

        return new UserEntity(
                userJoinRequest.getUsername(),
                userJoinRequest.getEmail(),
                userJoinRequest.getPassword(),
                additionalInformation
        );
    }
}
