package com.gam.api.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AuthConfig {
    @Value("${jwt.secret}")
    private String jwtSecretKey;

    @Value("${oauth.kakao.redirect.uri}")
    private String kakaoRedirectUri;

    @Value("${oauth.kakao.client.secret}")
    private String kakaoClientSecret;
}