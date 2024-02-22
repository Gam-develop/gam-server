package com.gam.api.domain.user.dto.response;

import lombok.Builder;



@Builder
public record UserScrapResponseDTO(
        Long targetUserId,
        String userName,
        boolean userScrap
) {
    public static UserScrapResponseDTO of(Long targetUserId, String userName, boolean userScrap) {
        return UserScrapResponseDTO.builder()
                .targetUserId(targetUserId)
                .userName(userName)
                .userScrap(userScrap)
                .build();
    }
}
