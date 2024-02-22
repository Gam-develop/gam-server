package com.gam.api.domain.social.service;

import com.gam.api.domain.social.dto.request.SocialLogoutRequestDTO;
import com.gam.api.domain.social.dto.request.SocialRefreshRequestDTO;
import com.gam.api.domain.social.dto.response.SocialLoginResponseDTO;
import com.gam.api.domain.social.dto.response.SocialRefreshResponseDTO;
import com.gam.api.domain.user.entity.ProviderType;

public interface SocialCommonService {
    void logout(SocialLogoutRequestDTO request);

    SocialRefreshResponseDTO refresh(SocialRefreshRequestDTO request);

    SocialLoginResponseDTO gamLogin(String thirdPartyUserId, ProviderType providerType, String deviceToken);
}
