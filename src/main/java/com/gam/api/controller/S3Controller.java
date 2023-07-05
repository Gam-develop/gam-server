package com.gam.api.controller;

import com.gam.api.common.message.ResponseMessage;
import com.gam.api.dto.s3.request.ImageDeleteRequestDTO;
import com.gam.api.dto.s3.request.PresignedRequestDTO;
import com.gam.api.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gam.api.common.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/s3")
public class S3Controller {
    private final S3Service s3Service;

    @GetMapping("/image")
    public ResponseEntity<ApiResponse> getPresignedUrl(@RequestParam String fileName) {
        val response = s3Service.getPresignedUrl(fileName);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_PRESIGNED_URL.getMessage(), response));
    }

    @PostMapping("/images")
    public ResponseEntity<ApiResponse> getPresignedUrls(@RequestBody PresignedRequestDTO request) {
        val response = s3Service.getPresignedUrls(request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_PRESIGNED_URLS.getMessage(), response));
    }

    @DeleteMapping("/image")
    public ResponseEntity<ApiResponse> getDeletePresignedUrl(@RequestBody ImageDeleteRequestDTO request) {
        s3Service.getDeletePresignedUrl(request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_DELETE_IMAGE.getMessage()));
    }
}