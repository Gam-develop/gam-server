package com.gam.api.dto.user.response;

import com.gam.api.entity.User;
import lombok.Builder;

@Builder
public record UserInfo( Long userId,
                                   String userName,
                                   String info,
                                   String detail,
                                   String email,
                                   int[] userTag) {
    public static UserInfo of(User user){
        return UserInfo.builder()
                .userId(user.getId())
                .userName(user.getUserName())
                .info(user.getInfo())
                .detail(user.getDetail())
                .email(user.getEmail())
                .userTag(user.getTags())
                .build();
    }
}
