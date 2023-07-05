package com.gam.api.dto.user.request;

import lombok.Builder;

@Builder
public record UserScrapRequestDto(
        Long targetUserId,
        boolean currentScrapStatus
) {
}
