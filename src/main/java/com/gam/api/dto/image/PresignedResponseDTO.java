package com.gam.api.dto.image;

import lombok.Builder;

@Builder
public record PresignedResponseDTO(String preSignedUrl, String fileName) {
    public static PresignedResponseDTO of(String preSignedUrl, String fileName) {
        return PresignedResponseDTO
                .builder()
                .preSignedUrl(preSignedUrl)
                .fileName(fileName)
                .build();
    }
}
