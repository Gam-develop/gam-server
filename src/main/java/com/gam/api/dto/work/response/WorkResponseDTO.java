package com.gam.api.dto.work.response;

import lombok.Builder;

@Builder
public record WorkResponseDTO(Long workId) {
    public static WorkResponseDTO of(Long workId) {
        return WorkResponseDTO.builder()
                .workId(workId)
                .build();
    }
}
