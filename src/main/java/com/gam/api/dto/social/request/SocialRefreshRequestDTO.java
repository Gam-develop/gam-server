package com.gam.api.dto.social.request;

public record SocialRefreshRequestDTO(
        String accessToken,
        String refreshToken
) {
}
