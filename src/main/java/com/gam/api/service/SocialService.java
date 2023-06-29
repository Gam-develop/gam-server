package com.gam.api.service;

import com.gam.api.dto.social.SocialLoginRequestDTO;
import com.gam.api.dto.social.SocialLoginResponseDTO;
import com.gam.api.entity.User;

public interface SocialService {
    SocialLoginResponseDTO login(SocialLoginRequestDTO request);
    void logout(Long userId);
    boolean chkProfileCompleted(User user);
}
