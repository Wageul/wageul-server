package com.wageul.wageul_server.common;

import com.wageul.wageul_server.common.exception.LoginException;
import com.wageul.wageul_server.common.request.GoogleAccessTokenRequest;
import com.wageul.wageul_server.common.response.GoogleAccessTokenResponse;
import com.wageul.wageul_server.common.response.GoogleAccountProfileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleClient {

    @Value("${google.client-id}")
    private String clientId;
    @Value("${google.client-secret}")
    private String clientSecret;
    @Value("${google.redirect-uri}")
    private String redirectUri;
    @Value("${google.authorization-code}")
    private String authorizationCode;
    @Value("${google.access-token-url}")
    private String accessTokenUrl;
    @Value("${google.profile-url}")
    private String profileUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public GoogleAccountProfileResponse getGoogleAccountProfile(String code) {
        final String accessToken = requestGoogleAccessToken(code);
        return requestGoogleAccountProfile(accessToken);
    }

    private String requestGoogleAccessToken(String code) {
        final String decodedCode = URLDecoder.decode(code, StandardCharsets.UTF_8);
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        final HttpEntity<GoogleAccessTokenRequest> httpEntity = new HttpEntity<>(
                new GoogleAccessTokenRequest(decodedCode, clientId, clientSecret, redirectUri, authorizationCode),
                headers
        );
        log.info(httpEntity.toString());
        final GoogleAccessTokenResponse response = restTemplate.exchange(
                accessTokenUrl, HttpMethod.POST, httpEntity, GoogleAccessTokenResponse.class
        ).getBody();
        return Optional.ofNullable(response)
                .orElseThrow(LoginException::new)
                .accessToken();
    }

    private GoogleAccountProfileResponse requestGoogleAccountProfile(String accessToken) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        final HttpEntity<GoogleAccessTokenRequest> httpEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(profileUrl, HttpMethod.GET, httpEntity, GoogleAccountProfileResponse.class)
                .getBody();
    }
}
