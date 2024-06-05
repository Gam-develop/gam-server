package com.gam.api.domain.user.dto.response;

import com.gam.api.domain.user.entity.User;
import lombok.Builder;

@Builder
public record UserProfileUpdateResponseDTO(
        String userName,
        String userInfo,
        String userDetail,
        String email,
        int[] tags
) {
    public static UserProfileUpdateResponseDTO of(User user ){
        return UserProfileUpdateResponseDTO.builder()
                .userName(user.getUserName())
                .userInfo(user.getInfo())
                .userDetail(user.getDetail())
                .email(user.getEmail())
                .tags(user.getTags())
                .build();
    }
}
