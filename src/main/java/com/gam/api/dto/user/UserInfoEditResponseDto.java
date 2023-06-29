package com.gam.api.dto.user;

import lombok.Builder;

import java.util.ArrayList;

@Builder
public record UserInfoEditResponseDto(
        String userInfo,
        String userDetail,
        ArrayList<String> tags
) {
    public static UserInfoEditResponseDto of(
            String userInfo,
            String userDetail,
            ArrayList<String> tags
    ){
        return UserInfoEditResponseDto
                .builder()
                .userInfo(userInfo)
                .userDetail(userDetail)
                .tags(tags)
                .build();
    }

}
