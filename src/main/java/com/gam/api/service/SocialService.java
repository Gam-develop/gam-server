package com.gam.api.service;

import com.gam.api.dto.social.SocialLoginRequestDTO;
import com.gam.api.dto.social.SocialLoginResponseDTO;

public interface SocialService {
    SocialLoginResponseDTO login(SocialLoginRequestDTO request);
    void logout(Long userId);
}
