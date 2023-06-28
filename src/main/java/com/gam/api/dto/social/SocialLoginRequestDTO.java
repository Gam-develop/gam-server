package com.gam.api.dto.social;

import com.gam.api.entity.ProviderType;

public record SocialLoginRequestDTO(String code, ProviderType providerType) {
}
