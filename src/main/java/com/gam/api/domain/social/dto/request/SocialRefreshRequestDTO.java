package com.gam.api.domain.social.dto.request;

public record SocialRefreshRequestDTO(
        String accessToken,
        String refreshToken
) {
}
