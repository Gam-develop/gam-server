package com.gam.api.service.user;

import com.gam.api.dto.user.request.UserExternalLinkRequestDto;
import com.gam.api.dto.user.request.UserOnboardRequestDTO;
import com.gam.api.dto.user.request.UserProfileUpdateRequestDto;
import com.gam.api.dto.user.request.UserScrapRequestDto;
import com.gam.api.dto.user.response.*;

public interface UserService {
    UserScrapResponseDto scrapUser(Long userId, UserScrapRequestDto request);
    UserExternalLinkResponseDto updateExternalLink(Long userId, UserExternalLinkRequestDto request);
    UserProfileUpdateResponseDto updateMyProfile(Long userId, UserProfileUpdateRequestDto request);
    UserMyProfileResponseDTO getMyProfile(Long userId);

    void onboardUser(Long userId, UserOnboardRequestDTO userOnboardRequestDTO);
    UserNameCheckResponseDTO checkUserNameDuplicated(String userName);
    UserProfileResponse getUserProfile(Long myId, Long userId);
}
