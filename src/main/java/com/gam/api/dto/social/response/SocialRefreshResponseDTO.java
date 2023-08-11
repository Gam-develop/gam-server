package com.gam.api.dto.social.response;

import lombok.Builder;

@Builder
public record SocialRefreshResponseDTO(
        String accessToken,
        String refreshToken
) {
    public static SocialRefreshResponseDTO of(String accessToken, String refreshToken) {
        return SocialRefreshResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
