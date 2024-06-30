package com.gam.api.domain.magazine.dto.query;

public record MagazineWithScrapQueryDTO(
        Long magazineId,
        String thumbnail,
        String magazineTitle,
        String interviewPerson,
        Long viewCount,
        boolean isScraped
) {
}
