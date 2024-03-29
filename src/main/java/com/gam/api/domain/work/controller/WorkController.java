package com.gam.api.domain.work.controller;

import com.gam.api.common.message.ResponseMessage;
import com.gam.api.domain.work.dto.request.WorkEditRequestDTO;
import com.gam.api.domain.work.dto.request.WorkCreateRequestDTO;
import com.gam.api.domain.work.dto.request.WorkDeleteRequestDTO;
import com.gam.api.domain.work.dto.request.WorkFirstAssignRequestDto;
import com.gam.api.domain.user.entity.GamUserDetails;
import com.gam.api.domain.work.service.WorkService;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.gam.api.common.ApiResponse;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/work")
public class WorkController {
    private final WorkService workService;

    @ApiOperation(value = "마이페이지 - 작업물 추가")
    @PostMapping("")
    public ResponseEntity<ApiResponse> createWork(
            @AuthenticationPrincipal GamUserDetails userDetails,
            @Valid @RequestBody WorkCreateRequestDTO request)
    {
        val response = workService.createWork(userDetails.getId(), request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_CREATE_WORK.getMessage(), response));
    }

    @ApiOperation(value = "마이페이지 - 작업물 삭제")
    @DeleteMapping("")
    public ResponseEntity<ApiResponse> deleteWork(
            @AuthenticationPrincipal GamUserDetails userDetails,
            @RequestBody WorkDeleteRequestDTO request)
    {
        val response = workService.deleteWork(userDetails.getId(), request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_DELETE_WORK.getMessage(), response));
    }

    @ApiOperation(value = "대표 작업물로 설정")
    @PatchMapping("/main")
    public ResponseEntity<ApiResponse> updateFirstWork(
            @AuthenticationPrincipal GamUserDetails userDetails,
            @RequestBody WorkFirstAssignRequestDto request)
    {
        workService.updateFirstWork(userDetails.getId(), request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_UPDATE_FIRST_WORK.getMessage()));
    }

    @ApiOperation(value = "마이페이지 - 작업물 수정")
    @PatchMapping("/edit")
    public ResponseEntity<ApiResponse> updateWork(
            @AuthenticationPrincipal GamUserDetails userDetails,
            @Valid @RequestBody WorkEditRequestDTO request)
    {
        val response = workService.updateWork(userDetails.getId(), request);

        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_UPDATE_WORK.getMessage(), response));
    }
}