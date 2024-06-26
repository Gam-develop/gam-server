package com.gam.api.domain.url.dto.response;

import lombok.Builder;

@Builder
public record UrlResponseDTO(String introUrl, String policyUrl, String agreementUrl, String makersUrl, String openSourceUrl) {
    public static UrlResponseDTO of(String introUrl, String policyUrl, String agreementUrl, String makersUrl, String openSourceUrl) {
        return UrlResponseDTO.builder()
                .introUrl(introUrl)
                .policyUrl(policyUrl)
                .agreementUrl(agreementUrl)
                .makersUrl(makersUrl)
                .openSourceUrl(openSourceUrl)
                .build();
    }
}
