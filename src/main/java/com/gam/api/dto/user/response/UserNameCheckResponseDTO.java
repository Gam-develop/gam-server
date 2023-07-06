package com.gam.api.dto.user.response;

import lombok.Builder;

@Builder
public record UserNameCheckResponseDTO(boolean isDuplicated) {
    public static UserNameCheckResponseDTO of(boolean isDuplicated) {
        return UserNameCheckResponseDTO.builder()
                .isDuplicated(isDuplicated)
                .build();
    }
}
