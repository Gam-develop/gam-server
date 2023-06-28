package com.gam.api.dto.social;

import lombok.Builder;

@Builder
public record SocialLoginResponseDTO(Boolean isUser, Long id, String accessToken) {
    public static  SocialLoginResponseDTO of(Boolean isUser, Long id, String accessToken) {
        return SocialLoginResponseDTO
                .builder()
                .isUser(isUser)
                .id(id)
                .accessToken(accessToken)
                .build();
    }
}
