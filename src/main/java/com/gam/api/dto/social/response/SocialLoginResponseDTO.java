package com.gam.api.dto.social.response;

import lombok.Builder;

@Builder
public record SocialLoginResponseDTO(Boolean isUser, Boolean isProfileCompleted, Long id, String accessToken, String appVersion) {
    public static SocialLoginResponseDTO of(Boolean isUser,  Boolean isProfileCompleted, Long id, String accessToken, String appVersion) {
        return SocialLoginResponseDTO.builder()
                .isUser(isUser)
                .isProfileCompleted(isProfileCompleted)
                .id(id)
                .accessToken(accessToken)
                .appVersion(appVersion)
                .build();
    }
}
