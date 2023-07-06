package com.gam.api.dto.user.response;

import lombok.Builder;

@Builder
public record UserWorkEditResponseDTO(
        Long workId
) {
    public static UserWorkEditResponseDTO of(Long workId){
        return UserWorkEditResponseDTO.builder()
                .workId(workId)
                .build();
    }
}
