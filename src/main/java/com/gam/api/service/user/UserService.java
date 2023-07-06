package com.gam.api.service.user;

import com.gam.api.dto.user.request.UserExternalLinkRequestDto;
import com.gam.api.dto.user.request.UserProfileUpdateRequestDto;
import com.gam.api.dto.user.request.UserScrapRequestDto;
import com.gam.api.dto.user.request.UserWorkEditRequestDTO;
import com.gam.api.dto.user.response.*;

public interface UserService {
    UserScrapResponseDto scrapUser(Long userId, UserScrapRequestDto request);
    UserExternalLinkResponseDto updateExternalLink(Long userId, UserExternalLinkRequestDto request);
    UserProfileUpdateResponseDto updateMyProfile(Long userId, UserProfileUpdateRequestDto request);
    UserMyProfileResponse getMyProfile(Long userId);
    UserWorkEditResponseDTO updateWork(Long userId, UserWorkEditRequestDTO request);
}
