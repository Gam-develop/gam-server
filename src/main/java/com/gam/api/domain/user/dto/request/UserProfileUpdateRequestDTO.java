package com.gam.api.domain.user.dto.request;

public record UserProfileUpdateRequestDTO(
        String userName,
        String userInfo,
        String userDetail,
        String email,
        int[] tags
) {
}
