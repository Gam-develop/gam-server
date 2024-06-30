package com.gam.api.domain.magazine.dto.response;

import com.gam.api.domain.magazine.dto.query.MagazineWithScrapQueryDTO;
import lombok.Builder;

import java.util.List;

@Builder
public record MagazineResponseDTO(
        List<MagazineResponseVO> magazineList
) {
    public static MagazineResponseDTO of(List<MagazineWithScrapQueryDTO> magazineList, String magazineBaseUrl) {
        return MagazineResponseDTO.builder()
                .magazineList(magazineList.stream().map(magazineWithScrap -> MagazineResponseVO.of(
                                magazineWithScrap.magazineId(),
                                magazineWithScrap.thumbnail(),
                                magazineWithScrap.magazineTitle(),
                                magazineWithScrap.interviewPerson(),
                                magazineWithScrap.viewCount(),
                                magazineWithScrap.isScraped(),
                                magazineBaseUrl
                        )).toList()
                )
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
    public static MagazineResponseVO of(
            Long magazineId,
            String thumbnail,
            String magazineTitle,
            String interviewPerson,
            Long viewCount,
            boolean isScraped,
            String magazineBaseUrl
    ) {
        return MagazineResponseVO.builder()
                .magazineId(magazineId)
                .thumbNail(thumbnail)
                .title(magazineTitle)
                .interviewPerson(interviewPerson)
                .magazineUrl(magazineBaseUrl + magazineId)
                .view(viewCount)
                .isScraped(isScraped)
                .build();
    }
}