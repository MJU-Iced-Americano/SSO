package com.mju.ssoclient.application;

import com.mju.ssoclient.application.dto.UserJoinRequest;
import com.mju.ssoclient.domain.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public String join(UserJoinRequest userJoinRequest) {
        String joinUserId = userRepository.join(userMapper.userFrom(userJoinRequest));
        log.info("UserId : {} Join Success ", joinUserId);
        return joinUserId;
    }
}
