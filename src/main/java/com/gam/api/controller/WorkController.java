package com.gam.api.controller;

import com.gam.api.common.util.AuthCommon;
import com.gam.api.common.message.ResponseMessage;
import com.gam.api.dto.work.request.WorkCreateRequestDTO;
import com.gam.api.dto.work.request.WorkDeleteRequestDTO;
import com.gam.api.dto.work.request.WorkFirstAssignRequestDto;
import com.gam.api.service.work.WorkService;
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
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_CREATE_WORK.getMessage(), response));
    }

    @DeleteMapping("")
    public ResponseEntity<ApiResponse> deleteWork(Principal principal, @RequestBody WorkDeleteRequestDTO request) {
        val userId = AuthCommon.getUser(principal);
        val response = workService.deleteWork(userId, request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_DELETE_WORK.getMessage(), response));
    }

    @PatchMapping("/main")
    public ResponseEntity<ApiResponse> updateFirstWork(Principal principal, @RequestBody WorkFirstAssignRequestDto request){
        val userId = AuthCommon.getUser(principal);
        workService.updateFirstWork(userId, request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_UPDATE_FIRST_WORK.getMessage()));
    }
}