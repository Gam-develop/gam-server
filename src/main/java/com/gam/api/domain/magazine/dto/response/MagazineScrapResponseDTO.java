package com.gam.api.domain.magazine.dto.response;

import lombok.Builder;

@Builder
public record MagazineScrapResponseDTO(
        Long targetMagazineId,
        boolean currentScrapStatus
) {
    public static MagazineScrapResponseDTO of(
            Long targetMagazineId,
            boolean currentScrapStatus
    ) {
        return MagazineScrapResponseDTO.builder()
                .targetMagazineId(targetMagazineId)
                .currentScrapStatus(currentScrapStatus)
                .build();
    }
}