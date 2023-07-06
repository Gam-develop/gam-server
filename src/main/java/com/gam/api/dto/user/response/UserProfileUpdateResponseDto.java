package com.gam.api.dto.user.response;

import com.gam.api.entity.User;
import lombok.Builder;

import java.util.List;

@Builder
public record UserProfileUpdateResponseDto(
        String userInfo,
        String userDetail,
        String email,
        int[] tags
) {
    public static UserProfileUpdateResponseDto of(User user ){
        return UserProfileUpdateResponseDto.builder()
                .userInfo(user.getInfo())
                .userDetail(user.getDetail())
                .email(user.getEmail())
                .tags(user.getTags())
                .build();
    }
}
