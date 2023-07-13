package com.gam.api.controller;

import com.gam.api.common.message.*;
import com.gam.api.common.ApiResponse;
import com.gam.api.common.util.AuthCommon;
import com.gam.api.dto.user.request.UserOnboardRequestDTO;
import com.gam.api.dto.user.request.UserUpdateLinkRequestDTO;
import com.gam.api.service.user.UserService;
import com.gam.api.dto.user.request.UserProfileUpdateRequestDTO;
import com.gam.api.dto.user.request.UserScrapRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/scrap")
    ResponseEntity<ApiResponse> scrapUser(Principal principal, @RequestBody UserScrapRequestDTO request) {
        val userId = AuthCommon.getUser(principal);
        val response = userService.scrapUser(userId, request);
        if (response.userScrap()){
            return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_USER_SCRAP.getMessage(),response));
        }
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_USER_DELETE_SCRAP.getMessage(),response));
    }

    @GetMapping("/my/profile")
    ResponseEntity<ApiResponse> getMyProfile(Principal principal) {
        val userId = AuthCommon.getUser(principal);
        val response = userService.getMyProfile(userId);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_MY_PROFILE.getMessage(), response));
    }

    @GetMapping("/my/portfolio")
    ResponseEntity<ApiResponse> getMyPortfolio(Principal principal) {
        val userId = AuthCommon.getUser(principal);
        val response = userService.getMyPortfolio(userId);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_PROTFOLIO_LIST.getMessage(), response));
    }
    @GetMapping("/portfolio/{userId}")
    ResponseEntity<ApiResponse> getPortfolio(Principal principal, @PathVariable Long userId) {
        val requestUserId = AuthCommon.getUser(principal);
        val response = userService.getPortfolio(requestUserId, userId);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_PROTFOLIO_LIST.getMessage(), response));
    }

    @PatchMapping("/introduce")
    ResponseEntity<ApiResponse> updateMyProfile(Principal principal, @RequestBody UserProfileUpdateRequestDTO request) {
        val userId = AuthCommon.getUser(principal);
        val response = userService.updateMyProfile(userId, request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_UPDATE_EXTERNAL_LINK.getMessage(), response));
    }

    @PostMapping("/onboard")
    ResponseEntity<ApiResponse> onboardUser(Principal principal, @RequestBody UserOnboardRequestDTO request) {
        val userId = AuthCommon.getUser(principal);
        userService.onboardUser(userId, request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_USER_ONBOARD.getMessage()));
    }

    @GetMapping("/name/check")
    ResponseEntity<ApiResponse> checkUserNameDuplicated(@RequestParam String userName) {
        val response = userService.checkUserNameDuplicated(userName);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_USER_NAME_DUPLICATE_CHECK.getMessage(), response));
    }

    @GetMapping("/scraps")
    ResponseEntity<ApiResponse> getUserScraps(Principal principal) {
        val userId = AuthCommon.getUser(principal);
        val response = userService.getUserScraps(userId);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_USER_SCRAPS.getMessage(), response));
    }

    @GetMapping("/detail/profile")
    ResponseEntity<ApiResponse> getUserProfile(Principal principal, @RequestParam Long userId) {
        val myId = AuthCommon.getUser(principal);
        val response = userService.getUserProfile(myId, userId);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_USER_PROFILE.getMessage(), response));
    }

    @GetMapping("/popular")
    ResponseEntity<ApiResponse> getPopularDesigners(Principal principal) {
        val userId = AuthCommon.getUser(principal);
        val response = userService.getPopularDesigners(userId);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_POPULAR_USER.getMessage(), response));
    }

    @PatchMapping("/link/instagram")
    ResponseEntity<ApiResponse> updateInstagramLink(Principal principal, UserUpdateLinkRequestDTO request) {
        val userId = AuthCommon.getUser(principal);
        userService.updateInstagramLink(userId, request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_UPDATE_INSTAGRAM_LINK.getMessage(),null));
    }

    @PatchMapping("/link/behance")
    ResponseEntity<ApiResponse> updateBehanceLink(Principal principal, UserUpdateLinkRequestDTO request) {
        val userId = AuthCommon.getUser(principal);
        userService.updateBehanceLink(userId, request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_UPDATE_BEHANCE_LINK.getMessage(),null));
    }

    @PatchMapping("/link/notion")
    ResponseEntity<ApiResponse> updateNotionLink(Principal principal, UserUpdateLinkRequestDTO request) {
        val userId = AuthCommon.getUser(principal);
         userService.updateNotionLink(userId, request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_UPDATE_NOTION_LINK.getMessage(),null));
    }
}
