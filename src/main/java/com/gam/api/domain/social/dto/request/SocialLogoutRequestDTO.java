package com.gam.api.domain.social.dto.request;

public record SocialLogoutRequestDTO(
        String accessToken,
        String refreshToken
) {
}
