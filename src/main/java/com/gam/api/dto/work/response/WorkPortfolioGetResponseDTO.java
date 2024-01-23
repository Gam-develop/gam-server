package com.gam.api.dto.work.response;

import com.gam.api.entity.User;
import com.gam.api.entity.Work;
import lombok.Builder;

import java.util.List;

@Builder
public record WorkPortfolioGetResponseDTO(
        boolean isScraped,
        String behanceLink,
        String instagramLink,
        String notionLink,
        List<WorkPortfolioGetResponseVO> works
) {
    public static WorkPortfolioGetResponseDTO of(boolean isScraped, User user, List<Work> works) {
        return WorkPortfolioGetResponseDTO.builder()
                .isScraped(isScraped)
                .behanceLink(user.getBehanceLink())
                .instagramLink(user.getInstagramLink())
                .notionLink(user.getNotionLink())
                .works(works.stream().map(WorkPortfolioGetResponseVO::of).toList())
                .build();
    }
}

@Builder
record WorkPortfolioGetResponseVO(
        Long workId,
        String workThumbNail,
        String workTitle,
        String workDetail
) {
    public static WorkPortfolioGetResponseVO of(Work work) {
        return WorkPortfolioGetResponseVO.builder()
                .workId(work.getId())
                .workThumbNail(work.getPhotoUrl())
                .workTitle(work.getTitle())
                .workDetail(work.getDetail())
                .build();
    }
}
