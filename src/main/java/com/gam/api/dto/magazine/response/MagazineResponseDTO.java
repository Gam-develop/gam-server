package com.gam.api.dto.magazine.response;

import com.gam.api.entity.Magazine;
import lombok.Builder;
import lombok.val;

import java.util.List;

@Builder
public record MagazineResponseDTO(
        List<MagazineResponseVO> magazineList
) {
    public static MagazineResponseDTO of(List<Magazine> magazineList, List<Long> userMagazineScraps) {
        return MagazineResponseDTO.builder()
                .magazineList(magazineList.stream().map(magazine -> {
                    val isScraped = userMagazineScraps.contains(magazine.getId()) ? true : false;
                    return MagazineResponseVO.of(magazine, isScraped);
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
        Long view,
        boolean isScraped
) {
    public static MagazineResponseVO of(Magazine magazine, boolean isScraped) {
        return MagazineResponseVO.builder()
                .magazineId(magazine.getId())
                .thumbNail(magazine.getThumbNail())
                .title(magazine.getMagazineTitle())
                .interviewPerson(magazine.getInterviewPerson())
                .view(magazine.getViewCount())
                .isScraped(isScraped)
                .build();
    }
}