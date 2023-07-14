package com.gam.api.dto.user.request;

import lombok.Builder;

@Builder
public record UserScrapRequestDTO(
        Long targetUserId,
        boolean currentScrapStatus
) {
}
