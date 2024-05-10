package com.gam.api.domain.magazine.dto.response;

import com.gam.api.domain.magazine.entity.Magazine;
import lombok.Builder;
import lombok.val;

import java.util.List;

@Builder
public record MagazineResponseDTO(
        List<MagazineResponseVO> magazineList
) {
    public static MagazineResponseDTO of(List<Magazine> magazineList, List<Long> userMagazineScraps, String magazineBaseUrl) {
        return MagazineResponseDTO.builder()
                .magazineList(magazineList.stream().map(magazine -> {
                    val isScraped = userMagazineScraps.contains(magazine.getId()) ? true : false;
                    return MagazineResponseVO.of(magazine, isScraped, magazineBaseUrl);
                }).toList())
                .build();
    }
}

@Builder
record MagazineResponseVO(
        Long magazineId,
        String thumbNail,
        String title,
        String interviewPerson,
        String magazineUrl,
        Long view,
        boolean isScraped
) {
    public static MagazineResponseVO of(Magazine magazine, boolean isScraped, String magazineBaseUrl) {
        return MagazineResponseVO.builder()
                .magazineId(magazine.getId())
                .thumbNail(magazine.getThumbNail())
                .title(magazine.getMagazineTitle())
                .interviewPerson(magazine.getInterviewPerson())
                .magazineUrl(magazineBaseUrl + magazine.getId())
                .view(magazine.getViewCount())
                .isScraped(isScraped)
                .build();
    }
}