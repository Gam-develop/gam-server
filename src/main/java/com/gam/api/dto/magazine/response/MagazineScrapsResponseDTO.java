package com.gam.api.dto.magazine.response;

import com.gam.api.entity.Magazine;
import lombok.Builder;

import java.util.List;

@Builder
public record MagazineScrapsResponseDTO(
       List<MagazineScrapsResponseVO> magazineScrapList
) {
    public static MagazineScrapsResponseDTO of(List<Magazine> magazines) {
        return MagazineScrapsResponseDTO.builder()
                .magazineScrapList(magazines.stream().map(MagazineScrapsResponseVO::of).toList())
                .build();
    }
}

@Builder
record MagazineScrapsResponseVO(
        Long magazineId,
        String thumbNail,
        String title,
        String interviewPerson,
        Long view,
        boolean isScraped
) {
    public static MagazineScrapsResponseVO of(Magazine magazine) {
        return MagazineScrapsResponseVO.builder()
                .magazineId(magazine.getId())
                .thumbNail(magazine.getThumbNail())
                .title(magazine.getMagazineTitle())
                .interviewPerson(magazine.getInterviewPerson())
                .view(magazine.getViewCount())
                .isScraped(true)
                .build();
    }
}