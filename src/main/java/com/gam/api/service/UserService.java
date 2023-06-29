package com.gam.api.service;

import com.gam.api.dto.user.UserScrapRequestDto;
import com.gam.api.dto.user.UserScrapResponseDto;

import com.gam.api.dto.user.UserInfoEditRequestDto;
import com.gam.api.dto.user.UserInfoEditResponseDto;

public interface UserService {
    UserScrapResponseDto postUserScrap(Long userId, UserScrapRequestDto request);
    UserInfoEditResponseDto editUserInfo(Long userId, UserInfoEditRequestDto request);
}