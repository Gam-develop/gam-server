package com.gam.api.controller;

import com.gam.api.common.ApiResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.Arrays;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final Environment env;

    @ApiOperation(value = "heal-check 컨트롤러")
    @GetMapping("/health")
    public ResponseEntity<ApiResponse> test() {
        return ResponseEntity.ok(ApiResponse.success("Hello gam Server!"));
    }
}