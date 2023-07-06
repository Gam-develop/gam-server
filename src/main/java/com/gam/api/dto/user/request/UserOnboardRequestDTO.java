package com.gam.api.dto.user.request;

public record UserOnboardRequestDTO(
        String userName,
        String info,
        int[] tags
) {
}
