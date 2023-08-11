package com.gam.api.service.social;

import com.gam.api.dto.social.request.SocialLogoutRequestDTO;
import com.gam.api.dto.social.request.SocialRefreshRequestDTO;
import com.gam.api.dto.social.response.SocialRefreshResponseDTO;

public interface SocialCommonService {
    void logout(SocialLogoutRequestDTO request);

    SocialRefreshResponseDTO refresh(SocialRefreshRequestDTO request);
}
