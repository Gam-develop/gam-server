package com.gam.api.controller;

import com.gam.api.common.ApiResponse;
import com.gam.api.common.message.ResponseMessage;
import com.gam.api.config.GamConfig;
import com.gam.api.dto.url.response.UrlResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/url")
public class UrlController {
    private final GamConfig gamConfig;

    @GetMapping("/info")
    ResponseEntity<ApiResponse> getIntroUrl() {
        val introductionUrl = UrlResponseDTO.of(gamConfig.getIntroUrl());
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_INTRO_URL.getMessage(), introductionUrl));
    }

    @GetMapping("/policy")
    ResponseEntity<ApiResponse> getPolicyUrl() {
        val policyUrl = UrlResponseDTO.of(gamConfig.getPolicyUrl());
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_POLICY_URL.getMessage(), policyUrl));
    }

    @GetMapping("/agreement")
    ResponseEntity<ApiResponse> getAgreementUrl() {
        val agreementUrl = UrlResponseDTO.of(gamConfig.getAgreementUrl());
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_AGREEMENT_URL.getMessage(), agreementUrl));
    }
}
