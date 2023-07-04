package com.gam.api.service;

import com.gam.api.dto.user.request.UserExternalLinkRequestDto;
import com.gam.api.dto.user.request.UserScrapRequestDto;
import com.gam.api.dto.user.response.UserExternalLinkResponseDto;
import com.gam.api.dto.user.response.UserScrapResponseDto;

public interface UserService {
    UserScrapResponseDto scrapUser(Long userId, UserScrapRequestDto request);
    UserExternalLinkResponseDto updateExternalLink(Long userId, UserExternalLinkRequestDto request);
}
