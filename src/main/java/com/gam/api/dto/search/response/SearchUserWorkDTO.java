package com.gam.api.dto.search.response;

import com.gam.api.entity.Work;
import lombok.Builder;

@Builder
public record SearchUserWorkDTO(
        String thumbNail,
        String title,
        String userName,
        int viewCount
) {
    public static SearchUserWorkDTO of (Work work) {
        return SearchUserWorkDTO.builder()
                .thumbNail(work.getPhotoUrl())
                .title(work.getTitle())
                .userName(work.getUser().getUserName())
                .viewCount(work.getViewCount())
                .build();
    }
}
