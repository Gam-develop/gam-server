package com.gam.api.dto.user.response;

import lombok.Builder;

@Builder
public record UserExternalLinkResponseDTO(
        String externalLink
){
    public static UserExternalLinkResponseDTO of(String externalLink){
        return UserExternalLinkResponseDTO.builder()
                .externalLink(externalLink)
                .build();
    }
}
