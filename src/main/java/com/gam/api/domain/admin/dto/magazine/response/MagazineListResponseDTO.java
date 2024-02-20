package com.gam.api.domain.admin.dto.magazine.response;

import com.gam.api.domain.magazine.entity.Magazine;
import lombok.Builder;


@Builder
public record MagazineListResponseDTO (
        Long magazineId,
        String magazineTitle,
        String interviewee
){
    public static MagazineListResponseDTO of(Magazine magazine) {
        return MagazineListResponseDTO.builder()
                .magazineId(magazine.getId())
                .magazineTitle(magazine.getMagazineTitle())
                .interviewee(magazine.getInterviewPerson())
                .build();
    }
}
