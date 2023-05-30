package com.mju.ssoclient.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mju.ssoclient.security.OauthToken;
import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class AuthController {
    @Value("${keycloak.client-url}")
    private String keycloakClientUrl;
    @Value("${keycloak.server-url}")
    private String keycloakServerUrl;

    @GetMapping(path = "/auth")
    public void auth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = request.getParameter("code");

        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded");
        headers.add("Accept", "application/json");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", "admin-client");
        params.add("client_secret", "admin-secret");

        params.add("redirect_uri", keycloakClientUrl + "/auth");
        params.add("grant_type", "authorization_code");
        params.add("code", URLEncoder.encode(code, "UTF-8"));

        HttpEntity<MultiValueMap<String, String>> githubTokenRequest =
                new HttpEntity<>(params, headers);
        ResponseEntity<String> accessTokenResponse = rt.exchange(
                keycloakServerUrl + "/realms/master/protocol/openid-connect/token",
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

        String redirectURL = "http://43.200.124.135/user-service/login";
        String authorizationHeader = "Bearer " + oauthToken.getAccessToken();
        response.setHeader("Authorization", authorizationHeader);
        Cookie cookie = new Cookie("SOCOA-SSO-TOKEN", oauthToken.getIdToken());
        cookie.setMaxAge(6 * 60 * 60);
        response.addCookie(cookie);
        response.sendRedirect(redirectURL);
    }
}
