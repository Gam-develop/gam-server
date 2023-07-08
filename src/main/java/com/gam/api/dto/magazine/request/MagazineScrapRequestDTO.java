package com.gam.api.dto.magazine.request;

public record MagazineScrapRequestDTO(
        Long targetMagazineId,
        boolean currentScrapStatus
) {
}
