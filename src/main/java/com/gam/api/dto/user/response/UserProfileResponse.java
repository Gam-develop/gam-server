package com.gam.api.dto.user.response;

import lombok.Builder;

@Builder
public record UserProfileResponse(
        boolean designerScrap,
        UserInfo UserInfo
) {
    public static UserProfileResponse of(boolean designerScrap, UserInfo userInfo){
        return UserProfileResponse.builder()
                .designerScrap(designerScrap)
                .UserInfo(userInfo)
                .build();
    }
}
