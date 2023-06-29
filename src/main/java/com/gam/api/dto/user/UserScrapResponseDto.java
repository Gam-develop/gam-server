package com.gam.api.dto.user;

import com.gam.api.repository.UserScrapRepository;
import lombok.Builder;



@Builder
public record UserScrapResponseDto (
        Long targetUserId,
        String userName,
        boolean userScrap
) {
    public static UserScrapResponseDto of(Long targetUserId, String userName, boolean userScrap) {
        return UserScrapResponseDto
                .builder()
                .targetUserId(targetUserId)
                .userName(userName)
                .userScrap(userScrap)
                .build();
    }
}
