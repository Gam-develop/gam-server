package com.gam.api.controller;

import com.gam.api.common.message.*;
import com.gam.api.common.ApiResponse;
import com.gam.api.dto.user.request.UserOnboardRequestDTO;
import com.gam.api.entity.GamUserDetails;
import com.gam.api.service.user.UserService;
import com.gam.api.dto.user.request.UserExternalLinkRequestDto;
import com.gam.api.dto.user.request.UserProfileUpdateRequestDto;
import com.gam.api.dto.user.request.UserScrapRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/scrap")
    ResponseEntity<ApiResponse> scrapUser(
            @AuthenticationPrincipal GamUserDetails userDetails,
            @RequestBody UserScrapRequestDto request
    ) {
        val response = userService.scrapUser(userDetails.getId(), request);
        if (response.userScrap()){
            return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_USER_SCRAP.getMessage(),response));
        }
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_USER_DELETE_SCRAP.getMessage(),response));
    }

    @PatchMapping("/link")
    ResponseEntity<ApiResponse> updateExternalLink(
            @AuthenticationPrincipal GamUserDetails userDetails,
            @RequestBody UserExternalLinkRequestDto request)
    {
        val response = userService.updateExternalLink(userDetails.getId(), request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_UPDATE_EXTERNAL_LINK.getMessage(), response));
    }

    @GetMapping("/my/profile")
    ResponseEntity<ApiResponse> getMyProfile(@AuthenticationPrincipal GamUserDetails userDetails) {
        val response = userService.getMyProfile(userDetails.getId());
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_MY_PROFILE.getMessage(), response));
    }

    @GetMapping("/my/portfolio")
    ResponseEntity<ApiResponse> getMyPortfolio(@AuthenticationPrincipal GamUserDetails userDetails) {
        val response = userService.getMyPortfolio(userDetails.getId());
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_PROTFOLIO_LIST.getMessage(), response));
    }
    @GetMapping("/portfolio/{userId}")
    ResponseEntity<ApiResponse> getPortfolio(
            @AuthenticationPrincipal GamUserDetails userDetails,
            @PathVariable Long userId)
    {
        val response = userService.getPortfolio(userDetails.getId(), userId);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_PROTFOLIO_LIST.getMessage(), response));
    }

    @PatchMapping("/introduce")
    ResponseEntity<ApiResponse> updateMyProfile(
            @AuthenticationPrincipal GamUserDetails userDetails,
            @RequestBody UserProfileUpdateRequestDto request)
    {
        val response = userService.updateMyProfile(userDetails.getId(), request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_UPDATE_EXTERNAL_LINK.getMessage(), response));
    }

    @PostMapping("/onboard")
    ResponseEntity<ApiResponse> onboardUser(
            @AuthenticationPrincipal GamUserDetails userDetails,
            @RequestBody UserOnboardRequestDTO request)
    {
        userService.onboardUser(userDetails.getId(), request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_USER_ONBOARD.getMessage()));
    }

    @GetMapping("/name/check")
    ResponseEntity<ApiResponse> checkUserNameDuplicated(@RequestParam String userName) {
        val response = userService.checkUserNameDuplicated(userName);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_USER_NAME_DUPLICATE_CHECK.getMessage(), response));
    }

    @GetMapping("/scraps")
    ResponseEntity<ApiResponse> getUserScraps(@AuthenticationPrincipal GamUserDetails userDetails) {
        val response = userService.getUserScraps(userDetails.getId());
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_USER_SCRAPS.getMessage(), response));
    }

    @GetMapping("/detail/profile")
    ResponseEntity<ApiResponse> getUserProfile(
            @AuthenticationPrincipal GamUserDetails userDetails,
            @RequestParam Long userId)
    {
        val response = userService.getUserProfile(userDetails.getId(), userId);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_USER_PROFILE.getMessage(), response));
    }

    @GetMapping("/popular")
    ResponseEntity<ApiResponse> getPopularDesigners(@AuthenticationPrincipal GamUserDetails userDetails) {
        val response = userService.getPopularDesigners(userDetails.getId());
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_POPULAR_USER.getMessage(), response));
    }

}
