package com.gam.api.controller;

import com.gam.api.common.ApiResponse;
import com.gam.api.common.message.ResponseMessage;
import com.gam.api.dto.admin.magazine.request.MagazineRequestDTO;
import com.gam.api.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/magazine/list")
    public ResponseEntity<ApiResponse> getMagazines() {
        val response = adminService.getMagazines();
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_MAGAZINE_LIST.getMessage(), response));
    }

    @DeleteMapping("/magazine/{magazineId}")
    public ResponseEntity<ApiResponse> deleteMagezine(@PathVariable Long magazineId) {
        adminService.deleteMagazine(magazineId);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_DELETE_MAGAZINE.getMessage()));
    }

    @PostMapping("/magazine")
    public ResponseEntity<ApiResponse> createMagazine(@RequestBody MagazineRequestDTO request) {
        adminService.createMagazine(request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_CREATE_MAGAZINE.getMessage()));
    }

    @PatchMapping("/magazine/{magazineId}")
    public ResponseEntity<ApiResponse> editMagazine(@PathVariable Long magazineId, @RequestBody MagazineRequestDTO request) {
        adminService.editMagazine(magazineId, request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_EDIT_MAGAZINE.getMessage()));
    }
}
