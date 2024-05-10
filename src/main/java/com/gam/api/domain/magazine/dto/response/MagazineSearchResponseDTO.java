package com.gam.api.domain.magazine.dto.response;

import com.gam.api.domain.magazine.entity.Magazine;
import lombok.Builder;

@Builder
public record MagazineSearchResponseDTO (
        String thumbNail,
        String title,
        String interviewPerson,
        String magazineUrl,
        Long viewCount
){
    public static MagazineSearchResponseDTO of(Magazine magazine, String magazineBaseUrl) {
        return MagazineSearchResponseDTO.builder()
                .thumbNail(magazine.getThumbNail())
                .title(magazine.getMagazineTitle())
                .interviewPerson(magazine.getInterviewPerson())
                .magazineUrl(magazineBaseUrl + magazine.getId())
                .viewCount(magazine.getViewCount())
                .build();
    }
}
