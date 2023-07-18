package com.gam.api.dto.search.response;

import com.gam.api.entity.User;
import com.gam.api.entity.Work;
import lombok.Builder;

@Builder
public record SearchUserWork(
        String thumbNail,
        String title,
        String userName,
        Long viewCount
) {
    public static SearchUserWork of (String thumbNail,String userName, Long viewCount) {
        return SearchUserWork.builder()
                .thumbNail(thumbNail)
                .userName(userName)
                .viewCount(viewCount)
                .build();
    }
}
