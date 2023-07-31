package com.gam.api.dto.url.response;

import lombok.Builder;

@Builder
public record UrlResponseDTO(String url) {
    public static UrlResponseDTO of(String url) {
        return UrlResponseDTO.builder()
                .url(url)
                .build();
    }
}
