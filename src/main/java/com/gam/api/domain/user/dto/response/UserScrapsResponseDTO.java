package com.gam.api.domain.user.dto.response;

import com.gam.api.domain.user.entity.User;
import lombok.Builder;

@Builder
public record UserScrapsResponseDTO (
        Long userId,
        String userName,
        String userThumbNail,
        Long userScrapId
){
    public static UserScrapsResponseDTO of(Long userScrapId, User user) {
        return UserScrapsResponseDTO.builder()
                .userId(user.getId())
                .userName(user.getUserName())
                .userThumbNail(user.getWorkThumbNail())
                .userScrapId(userScrapId)
                .build();
    }
}
