package com.gam.api.dto.work.response;

import com.gam.api.entity.Work;
import lombok.Builder;

import java.util.List;

@Builder
public record WorkPortfolioListResponseDTO(
        String additionalLink,
        List<WorkPortfolioListResponseVO> works
) {
    public static WorkPortfolioListResponseDTO of(String additionalLink, List<Work> works) {
        return WorkPortfolioListResponseDTO.builder()
                .additionalLink(additionalLink)
                .works(works.stream().map(WorkPortfolioListResponseVO::of).toList())
                .build();
    }
}

@Builder
record WorkPortfolioListResponseVO(
        Long workId,
        String workThumbNail,
        String workTitle,
        String workDetail
) {
    public static WorkPortfolioListResponseVO of(Work work) {
        return WorkPortfolioListResponseVO.builder()
                .workId(work.getId())
                .workThumbNail(work.getPhotoUrl())
                .workTitle(work.getTitle())
                .workDetail(work.getDetail())
                .build();
    }
}
