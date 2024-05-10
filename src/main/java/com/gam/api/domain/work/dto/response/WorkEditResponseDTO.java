package com.gam.api.domain.work.dto.response;

import lombok.Builder;

@Builder
public record WorkEditResponseDTO(
        Long workId
) {
    public static WorkEditResponseDTO of(Long workId){
        return WorkEditResponseDTO.builder()
                .workId(workId)
                .build();
    }
}
