package com.mju.ssoclient.presentation;

import com.mju.ssoclient.application.UserService;
import com.mju.ssoclient.application.dto.UserJoinRequest;
import java.net.URI;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@RequestMapping("/user")
@Controller
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity join(@RequestBody UserJoinRequest userJoinRequest) {
        String joinUserId = userService.join(userJoinRequest);
        return ResponseEntity.created(URI.create("/user/" + joinUserId)).build();
    }
}
