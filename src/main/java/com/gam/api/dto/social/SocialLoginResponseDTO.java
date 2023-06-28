package com.gam.api.dto.social;

import lombok.Builder;

@Builder
public record SocialLoginResponseDTO(Boolean isUser, Boolean isProfileCompleted, Long id, String accessToken) {
    public static SocialLoginResponseDTO of(Boolean isUser,  Boolean isProfileCompleted, Long id, String accessToken) {
        return SocialLoginResponseDTO
                .builder()
                .isUser(isUser)
                .isProfileCompleted(isProfileCompleted)
                .id(id)
                .accessToken(accessToken)
                .build();
    }
}
