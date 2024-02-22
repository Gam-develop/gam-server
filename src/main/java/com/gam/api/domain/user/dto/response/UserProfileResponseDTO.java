package com.gam.api.domain.user.dto.response;

import com.gam.api.domain.user.entity.User;
import lombok.Builder;

@Builder
public record UserProfileResponseDTO(
        boolean designerScrap,
        UserProfileResponseVO UserInfo
) {
    public static UserProfileResponseDTO of(boolean designerScrap, User user){
        return UserProfileResponseDTO.builder()
                .designerScrap(designerScrap)
                .UserInfo(UserProfileResponseVO.of(user))
                .build();
    }
}

@Builder
record UserProfileResponseVO(
        Long userId,
        String userName,
        String info,
        String detail,
        String email,
        int[] userTag
) {
    public static UserProfileResponseVO of(User user){
        return UserProfileResponseVO.builder()
                .userId(user.getId())
                .userName(user.getUserName())
                .info(user.getInfo())
                .detail(user.getDetail())
                .email(user.getEmail())
                .userTag(user.getTags() == null ? new int[0] : user.getTags())
                .build();
    }
}