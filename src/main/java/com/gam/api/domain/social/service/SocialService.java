package com.gam.api.domain.social.service;

import com.gam.api.domain.social.dto.request.SocialLoginRequestDTO;
import com.gam.api.domain.social.dto.response.SocialLoginResponseDTO;

public interface SocialService {
    SocialLoginResponseDTO login(SocialLoginRequestDTO request);
}
