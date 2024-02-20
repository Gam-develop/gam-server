package com.gam.api.domain.magazine.dto.request;

public record MagazineScrapRequestDTO(
        Long targetMagazineId,
        boolean currentScrapStatus
) {
}
