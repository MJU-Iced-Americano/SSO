package com.mju.ssoclient.presentation;

import com.mju.ssoclient.application.UserService;
import com.mju.ssoclient.application.dto.UserJoinRequest;
import java.net.URI;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequestMapping("/user")
@RestController
class UserController {

    private final UserService userService;
    @Value("${keycloak.client-url}")
    private String keycloakClientUrl;
    @Value("${keycloak.server-url}")
    private String keycloakServerUrl;

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
        return "redirect:http://" + keycloakServerUrl
                + "/realms/master/protocol/openid-connect/auth?response_type=code&client_id=admin-client&redirect_uri=http://"
                + keycloakClientUrl + "/auth&scope=openid&nonce=asb3";
    }
}
