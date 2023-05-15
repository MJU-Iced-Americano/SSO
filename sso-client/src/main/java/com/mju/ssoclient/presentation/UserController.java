package com.mju.ssoclient.presentation;

import com.mju.ssoclient.application.UserService;
import com.mju.ssoclient.application.dto.UserJoinRequest;

import java.net.URI;
import java.security.Principal;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequestMapping("/user")
@Controller
@Slf4j
class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity join(@RequestBody UserJoinRequest userJoinRequest) {
        String joinUserId = userService.join(userJoinRequest);
        return ResponseEntity.created(URI.create("/user/" + joinUserId)).build();
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("service completed");
    }


    @GetMapping("/login")
    public String login(Principal principal, HttpServletRequest request) {
        return "redirect:http://localhost:8080/realms/master/protocol/openid-connect/auth?response_type=code&client_id=SSOService&redirect_uri=http://localhost:80/auth&scope=openid&nonce=asb3";

    }
}
