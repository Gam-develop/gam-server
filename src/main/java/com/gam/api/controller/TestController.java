package com.gam.api.controller;

import com.gam.api.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/health")
    public ResponseEntity<ApiResponse> test() {
        return ResponseEntity.ok(ApiResponse.success("Hello gam Server!"));
    }

    @GetMapping("/test/deploy")
    public ResponseEntity<ApiResponse> deployTest() { return ResponseEntity.ok(ApiResponse.success("CI/CD 구축 완료")); }
}