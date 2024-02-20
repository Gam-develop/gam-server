package com.gam.api.domain.user.dto.request;

import lombok.Builder;

@Builder
public record UserScrapRequestDTO(
        Long targetUserId,
        boolean currentScrapStatus
) {
}
