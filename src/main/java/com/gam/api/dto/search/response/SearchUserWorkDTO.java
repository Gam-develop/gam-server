package com.gam.api.dto.search.response;

import lombok.Builder;

@Builder
public record SearchUserWorkDTO(
        String thumbNail,
        String title,
        String userName,
        Long viewCount
) {
    public static SearchUserWorkDTO of (String thumbNail, String userName, Long viewCount) {
        return SearchUserWorkDTO.builder()
                .thumbNail(thumbNail)
                .userName(userName)
                .viewCount(viewCount)
                .build();
    }
}
