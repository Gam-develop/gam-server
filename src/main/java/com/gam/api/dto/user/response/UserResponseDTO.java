package com.gam.api.dto.user.response;

import com.gam.api.entity.User;
import lombok.Builder;

@Builder
public record UserResponseDTO(
        Long userId,
        String userName,
        String userWorkThumbNail,
        int viewCount,
        int[] userTag,
        boolean designerScrap
) {
    public static UserResponseDTO of (User user, boolean designerScrap){
        return UserResponseDTO.builder()
                .userId(user.getId())
                .userName(user.getUserName())
                .userWorkThumbNail(user.getWorkThumbNail())
                .viewCount(user.getViewCount())
                .userTag(user.getTags())
                .designerScrap(designerScrap)
                .build();
    }
}
