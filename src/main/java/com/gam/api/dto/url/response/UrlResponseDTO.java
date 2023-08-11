package com.gam.api.dto.url.response;

import lombok.Builder;

@Builder
public record UrlResponseDTO(String introUrl, String policyUrl, String agreementUrl, String makersUrl) {
    public static UrlResponseDTO of(String introUrl, String policyUrl, String agreementUrl, String makersUrl) {
        return UrlResponseDTO.builder()
                .introUrl(introUrl)
                .policyUrl(policyUrl)
                .agreementUrl(agreementUrl)
                .makersUrl(makersUrl)
                .build();
    }
}
