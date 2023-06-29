package com.gam.api.dto.user;

import lombok.Builder;

@Builder
public record UserScrapRequestDto(
        Long targetUserId,
        boolean currentScrapStatus
) {
}
