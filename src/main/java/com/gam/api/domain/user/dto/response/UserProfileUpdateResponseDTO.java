package com.gam.api.domain.user.dto.response;

import com.gam.api.domain.user.entity.User;
import lombok.Builder;

@Builder
public record UserProfileUpdateResponseDTO(
        String userInfo,
        String userDetail,
        String email,
        int[] tags
) {
    public static UserProfileUpdateResponseDTO of(User user ){
        return UserProfileUpdateResponseDTO.builder()
                .userInfo(user.getInfo())
                .userDetail(user.getDetail())
                .email(user.getEmail())
                .tags(user.getTags())
                .build();
    }
}
