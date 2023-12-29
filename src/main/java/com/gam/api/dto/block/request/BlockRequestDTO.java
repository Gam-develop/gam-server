package com.gam.api.dto.block.request;

public record BlockRequestDTO(
        Long targetUserId,
        boolean currentBlockStatus) {
}
