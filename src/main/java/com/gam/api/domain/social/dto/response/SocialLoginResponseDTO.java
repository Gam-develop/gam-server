package com.gam.api.domain.social.dto.response;

import lombok.Builder;

@Builder
public record SocialLoginResponseDTO(
        Boolean isProfileCompleted,
        Long id,
        String accessToken,
        String refreshToken,
        String appVersion
) {
    public static SocialLoginResponseDTO of(Boolean isProfileCompleted, Long id, String accessToken, String refreshToken, String appVersion) {
        return SocialLoginResponseDTO.builder()
                .isProfileCompleted(isProfileCompleted)
                .id(id)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .appVersion(appVersion)
                .build();
    }
}
