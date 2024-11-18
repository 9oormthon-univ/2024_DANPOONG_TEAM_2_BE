package com.moa.moabackend.global.oauth.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moa.moabackend.auth.api.dto.response.UserInfo;
import com.moa.moabackend.auth.application.AuthService;
import com.moa.moabackend.global.oauth.api.dto.response.KakaoTokenResponse;
import com.moa.moabackend.global.oauth.exception.OAuthException;
import com.moa.moabackend.member.domain.SocialType;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@Transactional(readOnly = true)
public class KaKaoAuthService implements AuthService {

    private static final String JWT_DELIMITER = "\\.";

    @Value("${oauth.kakao.client-id}")
    private String kakaoClientId;

    @Value("${oauth.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    @Value("${oauth.kakao.id-token-url}")
    private String kakaoIdTokenUrl;

    private final ObjectMapper objectMapper;

    private final RestTemplate restTemplate = new RestTemplate();

    public KaKaoAuthService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String getProvider() {
        return String.valueOf(SocialType.KAKAO).toLowerCase();
    }

    @Override
    public UserInfo getUserInfo(String code) {
        String idToken = getIdToken(code);
        String decodePayload = getDecodePayload(idToken);

        UserInfo userInfo;
        try {
            userInfo = objectMapper.readValue(decodePayload, UserInfo.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return userInfo;
    }

    private String getIdToken(String authCode) {
        HttpEntity<MultiValueMap<String, String>> requestEntity = createRequestEntity(authCode);

        try {
            ResponseEntity<KakaoTokenResponse> responseTokenEntity = restTemplate.postForEntity(
                    kakaoIdTokenUrl,
                    requestEntity,
                    KakaoTokenResponse.class);

            return Objects.requireNonNull(responseTokenEntity.getBody()).getIdToken();
        } catch (RestClientException e) {
            throw new OAuthException();
        }
    }

    private HttpEntity<MultiValueMap<String, String>> createRequestEntity(String code) {
        return new HttpEntity<>(createRequestParams(code), createHttpHeaders());
    }

    private MultiValueMap<String, String> createRequestParams(String code) {
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();

        requestParams.add("code", code);
        requestParams.add("client_id", kakaoClientId);
        requestParams.add("grant_type", "authorization_code");
        requestParams.add("redirect_uri", kakaoRedirectUri);

        return requestParams;
    }

    private HttpHeaders createHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        return httpHeaders;
    }

    private String getDecodePayload(String idToken) {
        String payload = getPayload(idToken);
        return new String(Base64.getUrlDecoder().decode(payload), StandardCharsets.UTF_8);
    }

    private String getPayload(String idToken) {
        return idToken.split(JWT_DELIMITER)[1];
    }

}
