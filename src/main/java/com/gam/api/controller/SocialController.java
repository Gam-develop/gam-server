package com.gam.api.controller;

import com.gam.api.common.message.ResponseMessage;
import com.gam.api.service.social.SocialServiceProvider;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gam.api.common.ApiResponse;
import com.gam.api.dto.social.SocialLoginRequestDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/social")
public class SocialController {
    private final SocialServiceProvider socialServiceProvider;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody SocialLoginRequestDTO request) {
        val socialService = socialServiceProvider.getSocialService(request.providerType());
        val response = socialService.login(request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_LOGIN_UP.getMessage(), response));
    }
}