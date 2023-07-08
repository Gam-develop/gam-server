package com.gam.api.dto.user.response;

import com.gam.api.entity.User;
import lombok.Builder;
import lombok.Setter;

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
