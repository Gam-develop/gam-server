package com.gam.api.domain.user.dto.response;

import lombok.Builder;

@Builder
public record UserNameCheckResponseDTO(boolean isDuplicated) {
    public static UserNameCheckResponseDTO of(boolean isDuplicated) {
        return UserNameCheckResponseDTO.builder()
                .isDuplicated(isDuplicated)
                .build();
    }
}
