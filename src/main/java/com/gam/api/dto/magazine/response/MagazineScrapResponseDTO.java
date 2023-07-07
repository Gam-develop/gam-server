package com.gam.api.dto.magazine.response;

import lombok.Builder;

@Builder
public record MagazineScrapResponseDTO(
        Long tagretMagazineId,
        boolean currentScrapStatus
) {
    public static MagazineScrapResponseDTO of(
            Long tagretMagazineId,
            boolean currentScrapStatus
    ) {
        return MagazineScrapResponseDTO.builder()
                .tagretMagazineId(tagretMagazineId)
                .currentScrapStatus(currentScrapStatus)
                .build();
    }
}