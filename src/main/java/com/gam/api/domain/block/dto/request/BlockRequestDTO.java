package com.gam.api.domain.block.dto.request;

public record BlockRequestDTO(
        Long targetUserId,
        boolean currentBlockStatus) {
}
