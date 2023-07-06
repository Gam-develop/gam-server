package com.gam.api.dto.work.request;

public record WorkEditRequestDTO(
        Long workId,
        String image,
        String title,
        String detail
) {
}
