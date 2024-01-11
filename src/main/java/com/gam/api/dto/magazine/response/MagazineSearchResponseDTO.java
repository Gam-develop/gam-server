package com.gam.api.dto.magazine.response;

import com.gam.api.config.UrlConfig;
import com.gam.api.entity.Magazine;
import lombok.Builder;

@Builder
public record MagazineSearchResponseDTO (
        String thumbNail,
        String title,
        String interviewPerson,
        String magazineUrl,
        Long viewCount
){
    public static MagazineSearchResponseDTO of(Magazine magazine, UrlConfig urlConfig) {
        return MagazineSearchResponseDTO.builder()
                .thumbNail(magazine.getThumbNail())
                .title(magazine.getMagazineTitle())
                .interviewPerson(magazine.getInterviewPerson())
                .magazineUrl(urlConfig.getBaseUrl() + magazine.getId())
                .viewCount(magazine.getViewCount())
                .build();
    }
}
