package com.gam.api.domain.user.dto.response;

import com.gam.api.domain.work.entity.Work;
import lombok.Builder;
import lombok.val;

@Builder
public record SearchUserWorkDTO(
        String thumbNail,
        String title,
        String userName,
        Long userId,
        int viewCount
) {
    public static SearchUserWorkDTO of (Work work) {
        val user = work.getUser();
        return SearchUserWorkDTO.builder()
                .thumbNail(work.getPhotoUrl())
                .title(work.getTitle())
                .userName(user.getUserName())
                .userId(user.getId())
                .viewCount(user.getViewCount())
                .build();
    }
}
