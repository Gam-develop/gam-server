package com.gam.api.domain.magazine.dto.response;

import com.gam.api.domain.magazine.entity.Magazine;
import lombok.Builder;

import java.util.List;

@Builder
public record MagazineScrapsResponseDTO(
       List<MagazineScrapsResponseVO> magazineScrapList
) {
    public static MagazineScrapsResponseDTO of(List<Magazine> magazines, String magazineBaseUrl) {
        return MagazineScrapsResponseDTO.builder()
                .magazineScrapList(magazines.stream().map(magazine -> MagazineScrapsResponseVO.of(magazine, magazineBaseUrl)).toList())
                .build();
    }
}

@Builder
record MagazineScrapsResponseVO(
        Long magazineId,
        String thumbNail,
        String title,
        String interviewPerson,
        String magazineUrl,
        Long view,
        boolean isScraped
) {
    public static MagazineScrapsResponseVO of(Magazine magazine, String magazineBaseUrl) {
        return MagazineScrapsResponseVO.builder()
                .magazineId(magazine.getId())
                .thumbNail(magazine.getThumbNail())
                .title(magazine.getMagazineTitle())
                .interviewPerson(magazine.getInterviewPerson())
                .magazineUrl(magazineBaseUrl + magazine.getId())
                .view(magazine.getViewCount())
                .isScraped(true)
                .build();
    }
}