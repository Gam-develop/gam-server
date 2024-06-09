package com.gam.api.domain.url.controller;

import com.gam.api.common.ApiResponse;
import com.gam.api.common.message.ResponseMessage;
import com.gam.api.config.GamConfig;
import com.gam.api.domain.url.dto.response.UrlResponseDTO;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/url")
public class UrlController {
    private final GamConfig gamConfig;

    @ApiOperation(value = "주소 가져오기")
    @GetMapping("")
    ResponseEntity<ApiResponse> getUrl() {
        val introductionUrl = UrlResponseDTO.of(
                gamConfig.getIntroUrl(),
                gamConfig.getPolicyUrl(),
                gamConfig.getAgreementUrl(),
                gamConfig.getMakersUrl(),
                gamConfig.getOpenSourceUrl()
        );
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_URL.getMessage(), introductionUrl));
    }
}
