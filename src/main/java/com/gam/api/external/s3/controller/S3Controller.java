package com.gam.api.external.s3.controller;

import com.gam.api.common.message.ResponseMessage;
import com.gam.api.external.s3.dto.request.PresignedRequestDTO;
import com.gam.api.external.s3.service.S3ServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gam.api.common.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/s3")
public class S3Controller {
    private final S3ServiceImpl s3Service;

    @ApiOperation(value = "이미지 단일 업로드")
    @GetMapping("/image")
    public ResponseEntity<ApiResponse> getPresignedUrl(@RequestParam String fileName) {
        val response = s3Service.getPresignedUrl(fileName);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_PRESIGNED_URL.getMessage(), response));
    }

    @ApiOperation(value = "이미지 다중 업로드")
    @PostMapping("/images")
    public ResponseEntity<ApiResponse> getPresignedUrls(@RequestBody PresignedRequestDTO request) {
        val response = s3Service.getPresignedUrls(request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_PRESIGNED_URLS.getMessage(), response));
    }
}