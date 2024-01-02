package com.gam.api.service.social;

import com.gam.api.dto.social.request.SocialLoginRequestDTO;
import com.gam.api.dto.social.response.SocialLoginResponseDTO;

public interface SocialService {
    SocialLoginResponseDTO login(SocialLoginRequestDTO request);
}
