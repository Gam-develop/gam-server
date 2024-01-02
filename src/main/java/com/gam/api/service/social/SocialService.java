package com.gam.api.service.social;

import com.gam.api.dto.social.request.SocialLoginRequestDTO;
import com.gam.api.dto.social.response.SocialLoginResponseDTO;
import com.gam.api.entity.User;

public interface SocialService {
    SocialLoginResponseDTO login(SocialLoginRequestDTO request);
}
