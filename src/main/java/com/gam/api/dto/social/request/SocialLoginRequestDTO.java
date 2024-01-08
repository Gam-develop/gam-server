package com.gam.api.dto.social.request;

import com.gam.api.entity.ProviderType;

public record SocialLoginRequestDTO(String token, ProviderType providerType) {
}
