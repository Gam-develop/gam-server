package com.gam.api.controller;

import com.gam.api.common.message.ResponseMessage;
import com.gam.api.dto.social.request.SocialLogoutRequestDTO;
import com.gam.api.dto.social.request.SocialRefreshRequestDTO;
import com.gam.api.service.social.SocialCommonService;
import com.gam.api.service.social.SocialServiceProvider;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gam.api.common.ApiResponse;
import com.gam.api.dto.social.request.SocialLoginRequestDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/social")
public class SocialController {
    private final SocialServiceProvider socialServiceProvider;
    private final SocialCommonService socialCommonService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody SocialLoginRequestDTO request) {
        val socialService = socialServiceProvider.getSocialService(request.providerType());
        val response = socialService.login(request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_LOGIN.getMessage(), response));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> login(@RequestBody SocialLogoutRequestDTO request) {
        socialCommonService.logout(request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_LOGOUT.getMessage()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse> login(@RequestBody SocialRefreshRequestDTO request) {
        val response = socialCommonService.refresh(request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_REFRESH_TOKEN.getMessage(), response));
    }
}