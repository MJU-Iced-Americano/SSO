package com.mju.ssoclient.domain;

import org.mju.domain.UserEntity;

public interface UserRepository {
    String join(UserEntity user);
}
