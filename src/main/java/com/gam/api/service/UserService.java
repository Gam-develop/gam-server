package com.gam.api.service;

import com.gam.api.dto.user.UserScrapRequestDto;
import com.gam.api.dto.user.UserScrapResponseDto;

public interface UserService {
    UserScrapResponseDto scrapUser(Long userId, UserScrapRequestDto request);

}
