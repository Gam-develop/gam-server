package com.gam.api.controller;

import com.gam.api.common.ApiResponse;
import com.gam.api.common.message.ResponseMessage;
import com.gam.api.service.magazine.MagazineService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/magazine")
public class MagazineController {
    private final MagazineService magazineService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getMagazineDetail(@RequestParam Long magazineId) {
        val response = magazineService.getMagazineDetail(magazineId);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_MAGAZINE_DETAIL.getMessage(), response));
    }
}
