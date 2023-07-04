package com.gam.api.dto.work.response;

import com.gam.api.dto.user.UserScrapResponseDto;
import lombok.Builder;

@Builder
public record WorkCreateResponseDTO(Long workId) {
    public static WorkCreateResponseDTO of(Long workId) {
        return WorkCreateResponseDTO
                .builder()
                .workId(workId)
                .build();
    }
}
