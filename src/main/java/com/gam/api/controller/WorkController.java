package com.gam.api.controller;

import com.gam.api.common.util.AuthCommon;
import com.gam.api.common.message.ResponseMessage;
import com.gam.api.dto.work.request.WorkCreateRequestDTO;
import com.gam.api.service.WorkService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gam.api.common.ApiResponse;

import java.security.Principal;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/work")
public class WorkController {
    private final WorkService workService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> createWork(Principal principal, @RequestBody WorkCreateRequestDTO request) {
        val userId = AuthCommon.getUser(principal);
        val response = workService.createWork(userId, request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_LOGIN_UP.getMessage(), response));
    }
}