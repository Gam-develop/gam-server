package com.gam.api.service.social;

import com.gam.api.dto.social.request.SocialLogoutRequestDTO;
import com.gam.api.dto.social.request.SocialRefreshRequestDTO;
import com.gam.api.dto.social.response.SocialLoginResponseDTO;
import com.gam.api.dto.social.response.SocialRefreshResponseDTO;
import com.gam.api.entity.ProviderType;

public interface SocialCommonService {
    void logout(SocialLogoutRequestDTO request);

    SocialRefreshResponseDTO refresh(SocialRefreshRequestDTO request);

    SocialLoginResponseDTO gamLogin(String thirdPartyUserId, ProviderType providerType, String deviceToken);
}
