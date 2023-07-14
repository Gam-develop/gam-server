package com.gam.api.dto.user.request;

public record UserProfileUpdateRequestDTO(
        String userInfo,
        String userDetail,
        String email,
        int[] tags
) {
}
