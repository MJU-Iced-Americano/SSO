package com.mju.ssoclient.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mju.ssoclient.security.OauthToken;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@RestController
public class AuthController {

    // Client 설정헤서 Valid redirect URIs을 http://localhost:80/auth로 설정해야 함.
    @GetMapping(path = "/auth")
    public OauthToken auth(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        System.out.println("코드 받아졌나" + code);

        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded");
        headers.add("Accept", "application/json");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        // keycloak꺼 client_id, client_secret
        params.add("client_id", "");
        params.add("client_secret", "");

        params.add("redirect_uri", "http://localhost:80/auth");
        params.add("grant_type", "authorization_code");
        params.add("code", URLEncoder.encode(code,"UTF-8"));

        HttpEntity<MultiValueMap<String, String>> githubTokenRequest =
                new HttpEntity<>(params, headers);
        ResponseEntity<String> accessTokenResponse = rt.exchange(
                "http://localhost:8080/realms/master/protocol/openid-connect/token",
                HttpMethod.POST,
                githubTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        OauthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(accessTokenResponse.getBody(), OauthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return oauthToken;

    }
}
