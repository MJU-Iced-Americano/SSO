package com.mju.ssoclient.domain;

import com.mju.ssoclient.application.dto.UserResponse;
import org.mju.domain.UserEntity;

public interface UserRepository {
    String join(UserEntity user);

    UserResponse findById(String userId);
}
