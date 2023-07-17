package com.gam.api.dto.magazine.response;

import com.gam.api.entity.Magazine;
import lombok.Builder;

@Builder
public record MagazineSearchResponseDTO (
        String thumbNail,
        String title,
        String interviewPerson,
        Long viewCount,
        boolean magazineScrap
){
    public static MagazineSearchResponseDTO of(Magazine magazine, boolean magazineScrap) {
        return MagazineSearchResponseDTO.builder()
                .thumbNail(magazine.getThumbNail())
                .title(magazine.getMagazineTitle())
                .interviewPerson(magazine.getInterviewPerson())
                .viewCount(magazine.getViewCount())
                .magazineScrap(magazineScrap)
                .build();
    }
}
