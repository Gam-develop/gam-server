package com.gam.api.controller;

import com.gam.api.common.ApiResponse;
import com.gam.api.common.message.ResponseMessage;
import com.gam.api.dto.admin.magazine.request.MagazineRequestDTO;
import com.gam.api.entity.GamUserDetails;
import com.gam.api.service.admin.AdminService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin")
public class AdminController {

    private final AdminService adminService;

    @ApiOperation(value = "매거진 - 목록")
    @GetMapping("/magazine/list")
    public ResponseEntity<ApiResponse> getMagazines() {
        val response = adminService.getMagazines();
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_MAGAZINE_LIST.getMessage(), response));
    }

    @ApiOperation(value = "매거진 - 삭제")
    @DeleteMapping("/magazine/{magazineId}")
    public ResponseEntity<ApiResponse> deleteMagezine(@PathVariable Long magazineId) {
        adminService.deleteMagazine(magazineId);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_DELETE_MAGAZINE.getMessage()));
    }

    @ApiOperation(value = "매거진 - 생성")
    @PostMapping("/magazine")
    public ResponseEntity<ApiResponse> createMagazine(@RequestBody MagazineRequestDTO request) {
        adminService.createMagazine(request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_CREATE_MAGAZINE.getMessage()));
    }

    @ApiOperation(value = "관리자 매거진 - 세부")
    @GetMapping("/magazine/detail")
    public ResponseEntity<ApiResponse> getMagazineDetail(@RequestParam Long magazineId, @AuthenticationPrincipal GamUserDetails userDetails) {
        val response = adminService.getMagazineDetail(magazineId, userDetails.getId());
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_MAGAZINE_DETAIL.getMessage(), response));
    }


    @ApiOperation(value = "매거진 - 수정")
    @PatchMapping("/magazine/{magazineId}")
    public ResponseEntity<ApiResponse> editMagazine(@PathVariable Long magazineId, @RequestBody MagazineRequestDTO request) {
        adminService.editMagazine(magazineId, request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_EDIT_MAGAZINE.getMessage()));
    }
}
