package com.gam.api.domain.user.dto.response;

import com.gam.api.domain.user.entity.User;
import lombok.Builder;

import java.util.Objects;

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
                .userWorkThumbNail(Objects.isNull(user.getWorkThumbNail()) ? "" : user.getWorkThumbNail())
                .viewCount(user.getViewCount())
                .userTag(user.getTags() == null ? new int[0] : user.getTags())
                .designerScrap(designerScrap)
                .build();
    }
}
