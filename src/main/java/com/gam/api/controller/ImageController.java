package com.gam.api.controller;

import com.gam.api.common.ResponseMessage;
import com.gam.api.dto.image.PresignedRequestDTO;
import com.gam.api.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gam.api.common.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/image")
public class ImageController {
    private final S3Service s3Service;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getPresignedUrl(@RequestParam String fileName) {
        val response = s3Service.getPresignedUrl(fileName);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_PRESIGNED_URL.getMessage(), response));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> getPresignedUrls(@RequestBody PresignedRequestDTO request) {
        val response = s3Service.getPresignedUrls(request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_PRESIGNED_URLS.getMessage(), response));
    }
}