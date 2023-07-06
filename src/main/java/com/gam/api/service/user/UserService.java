package com.gam.api.service.user;

import com.gam.api.dto.user.request.UserExternalLinkRequestDto;
import com.gam.api.dto.user.request.UserOnboardRequestDTO;
import com.gam.api.dto.user.request.UserProfileUpdateRequestDto;
import com.gam.api.dto.user.request.UserScrapRequestDto;
import com.gam.api.dto.user.response.UserScrapResponseDto;
import com.gam.api.dto.user.response.UserExternalLinkResponseDto;
import com.gam.api.dto.user.response.UserProfileUpdateResponseDto;
import com.gam.api.dto.user.response.UserMyProfileResponse;
import com.gam.api.dto.user.response.UserNameCheckResponseDTO;
import com.gam.api.dto.work.response.WorkEditResponseDTO;
import com.gam.api.dto.work.request.WorkEditRequestDTO;

public interface UserService {
    UserScrapResponseDto scrapUser(Long userId, UserScrapRequestDto request);
    UserExternalLinkResponseDto updateExternalLink(Long userId, UserExternalLinkRequestDto request);
    UserProfileUpdateResponseDto updateMyProfile(Long userId, UserProfileUpdateRequestDto request);
    UserMyProfileResponse getMyProfile(Long userId);

    void onboardUser(Long userId, UserOnboardRequestDTO userOnboardRequestDTO);
    UserNameCheckResponseDTO checkUserNameDuplicated(String userName);
}
