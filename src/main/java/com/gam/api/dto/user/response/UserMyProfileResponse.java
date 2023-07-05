package com.gam.api.dto.user.response;


import com.gam.api.entity.User;
import lombok.Builder;

import java.util.List;

@Builder
public record UserMyProfileResponse(
        Long userId,
        String userName,
        String info,
        String detail,
        String email,
        List<Integer> userTag
) {
    public static UserMyProfileResponse of(User user){
        return UserMyProfileResponse.builder()
                .userId(user.getId())
                .userName(user.getUserName())
                .info(user.getInfo())
                .detail(user.getDetail())
                .email(user.getEmail())
                .userTag(user.getTags())
                .build();
    }
}
