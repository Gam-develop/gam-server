package com.gam.api.domain.social.dto.response;

import lombok.Builder;

@Builder
public record SocialRefreshResponseDTO(
        Boolean isProfileCompleted,
        Long id,
        String accessToken,
        String refreshToken,
        String appVersion
) {
    public static SocialRefreshResponseDTO of(Boolean isProfileCompleted, Long id, String accessToken, String refreshToken, String appVersion) {
        return SocialRefreshResponseDTO.builder()
                .isProfileCompleted(isProfileCompleted)
                .id(id)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .appVersion(appVersion)
                .build();
    }
}
