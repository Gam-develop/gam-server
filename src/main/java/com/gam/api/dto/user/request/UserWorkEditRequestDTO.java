package com.gam.api.dto.user.request;

public record UserWorkEditRequestDTO(
        Long workId,
        String image,
        String title,
        String detail
) {
}
