package com.gam.api.domain.work.dto.request;

public record WorkCreateRequestDTO(
        String image,
        String title,
        String detail) {
}
