package com.gam.api.controller;

import com.gam.api.common.ApiResponse;
import com.gam.api.common.message.ResponseMessage;
import com.gam.api.service.manager.ManagerService;
import com.gam.api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/manager")
public class ManagerController {

    private final ManagerService managerService;

    @GetMapping("/magazine/list")
    public ResponseEntity<ApiResponse> getMagazines() {
        val response = managerService.getMagazines();
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_MAGAZINE_LIST.getMessage(), response));
    }

    @DeleteMapping("/magazine/{magazineId}")
    public ResponseEntity<ApiResponse> deleteMagezine(@PathVariable Long magazineId) {
        managerService.deleteMagazine(magazineId);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_DELETE_MAGAZINE.getMessage()));
    }


}
