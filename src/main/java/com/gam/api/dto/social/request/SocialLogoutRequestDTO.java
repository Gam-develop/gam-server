package com.gam.api.dto.social.request;

public record SocialLogoutRequestDTO(
        String accessToken,
        String refreshToken
) {
}
