package com.gam.api.dto.user.request;

import java.util.List;

public record UserProfileUpdateRequestDto(
        String userInfo,
        String userDetail,
        String email,
        Integer[] tags
) {
}
