package com.gam.api.dto.user.response;

import lombok.Builder;

@Builder
public record UserExternalLinkResponseDto (
        String externalLink
){
    public static UserExternalLinkResponseDto of(String externalLink){
        return UserExternalLinkResponseDto.builder()
                .externalLink(externalLink)
                .build();
    }
}
