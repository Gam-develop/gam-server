package com.gam.api.domain.social.controller;

import com.gam.api.common.message.ResponseMessage;
import com.gam.api.domain.social.dto.request.SocialLogoutRequestDTO;
import com.gam.api.domain.social.dto.request.SocialRefreshRequestDTO;
import com.gam.api.domain.social.service.SocialCommonService;
import com.gam.api.domain.social.service.SocialServiceProvider;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gam.api.common.ApiResponse;
import com.gam.api.domain.social.dto.request.SocialLoginRequestDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/social")
public class SocialController {
    private final SocialServiceProvider socialServiceProvider;
    private final SocialCommonService socialCommonService;

    @ApiOperation(value = "소셜로그인")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody SocialLoginRequestDTO request) {
        val socialService = socialServiceProvider.getSocialService(request.providerType());
        val response = socialService.login(request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_LOGIN.getMessage(), response));
    }

    @ApiOperation(value = "로그아웃")
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> login(@RequestBody SocialLogoutRequestDTO request) {
        socialCommonService.logout(request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_LOGOUT.getMessage()));
    }

    @ApiOperation(value = "토큰 리프레시")
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse> login(@RequestBody SocialRefreshRequestDTO request) {
        val response = socialCommonService.refresh(request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_REFRESH_TOKEN.getMessage(), response));
    }
}