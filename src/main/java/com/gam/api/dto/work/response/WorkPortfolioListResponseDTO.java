package com.gam.api.dto.work.response;

import com.gam.api.entity.User;
import com.gam.api.entity.Work;
import lombok.Builder;

import java.util.List;

@Builder
public record WorkPortfolioListResponseDTO(
        String behanceLink,
        String instagramLink,
        String notionLink,
        List<WorkPortfolioListResponseVO> works
) {
    public static WorkPortfolioListResponseDTO of(User user, List<Work> works) {
        return WorkPortfolioListResponseDTO.builder()
                .behanceLink(user.getBehanceLink())
                .instagramLink(user.getInstagramLink())
                .notionLink(user.getNotionLink())
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
