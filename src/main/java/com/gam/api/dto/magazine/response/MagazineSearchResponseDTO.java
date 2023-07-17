package com.gam.api.dto.magazine.response;

import com.gam.api.entity.Magazine;
import lombok.Builder;

@Builder
public record MagazineSearchResponseDTO (
        String thumbNail,
        String title,
        String interviewPerson,
        Long viewCount
){
    public static MagazineSearchResponseDTO of(Magazine magazine) {
        return MagazineSearchResponseDTO.builder()
                .thumbNail(magazine.getThumbNail())
                .title(magazine.getMagazineTitle())
                .interviewPerson(magazine.getInterviewPerson())
                .viewCount(magazine.getViewCount())
                .build();
    }
}
