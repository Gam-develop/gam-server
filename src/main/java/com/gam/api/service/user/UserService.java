package com.gam.api.service.user;

import com.gam.api.dto.user.request.UserOnboardRequestDTO;
import com.gam.api.dto.user.request.UserProfileUpdateRequestDTO;
import com.gam.api.dto.user.request.UserScrapRequestDTO;
import com.gam.api.dto.user.request.UserUpdateLinkRequestDTO;
import com.gam.api.dto.user.response.*;
import com.gam.api.dto.work.response.*;

import java.util.List;


public interface UserService {
    UserScrapResponseDTO scrapUser(Long userId, UserScrapRequestDTO request);
    UserProfileUpdateResponseDTO updateMyProfile(Long userId, UserProfileUpdateRequestDTO request);
    UserMyProfileResponseDTO getMyProfile(Long userId);
    void onboardUser(Long userId, UserOnboardRequestDTO userOnboardRequestDTO);
    UserNameCheckResponseDTO checkUserNameDuplicated(String userName);
    UserProfileResponseDTO getUserProfile(Long myId, Long userId);
    List<UserScrapsResponseDTO> getUserScraps(Long userId);
    List<UserResponseDTO> getPopularDesigners(Long userId);
    WorkPortfolioListResponseDTO getMyPortfolio(Long userId);
    WorkPortfolioGetResponseDTO getPortfolio(Long requestUserId, Long userId);
    List<UserDiscoveryResponseDTO> getDiscoveryUsers(Long userId);
    void updateInstagramLink(Long userId, UserUpdateLinkRequestDTO request);
    void updateBehanceLink(Long userId, UserUpdateLinkRequestDTO request);
    void updateNotionLink(Long userId, UserUpdateLinkRequestDTO request);
    }
