package com.gam.api.domain.social.dto.request;

import com.gam.api.domain.user.entity.ProviderType;

public record SocialLoginRequestDTO(String token, ProviderType providerType, String deviceToken) {
}
