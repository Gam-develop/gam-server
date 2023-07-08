package com.gam.api.service.user;

import com.gam.api.dto.user.request.UserExternalLinkRequestDto;
import com.gam.api.dto.user.request.UserOnboardRequestDTO;
import com.gam.api.dto.user.request.UserProfileUpdateRequestDto;
import com.gam.api.dto.user.request.UserScrapRequestDto;
import com.gam.api.dto.user.response.*;

import java.util.List;

public interface UserService {
    UserScrapResponseDTO scrapUser(Long userId, UserScrapRequestDto request);
    UserExternalLinkResponseDTO updateExternalLink(Long userId, UserExternalLinkRequestDto request);
    UserProfileUpdateResponseDTO updateMyProfile(Long userId, UserProfileUpdateRequestDto request);
    UserMyProfileResponseDTO getMyProfile(Long userId);

    void onboardUser(Long userId, UserOnboardRequestDTO userOnboardRequestDTO);
    UserNameCheckResponseDTO checkUserNameDuplicated(String userName);
    UserProfileResponseDTO getUserProfile(Long myId, Long userId);
    List<UserScrapsResponseDTO> getUserScraps(Long userId);
    List<UserResponseDTO> getPopularDesigners(Long userId);
}
