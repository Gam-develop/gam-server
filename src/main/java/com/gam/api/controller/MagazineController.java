package com.gam.api.controller;

import com.gam.api.common.ApiResponse;
import com.gam.api.common.message.ResponseMessage;
import com.gam.api.common.util.AuthCommon;
import com.gam.api.dto.magazine.request.MagazineScrapRequestDTO;
import com.gam.api.service.magazine.MagazineService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/magazine")
public class MagazineController {
    private final MagazineService magazineService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getMagazines(Principal principal) {
        val userId = AuthCommon.getUser(principal);
        val response = magazineService.getMagazines(userId);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_MAGAZINE_LIST.getMessage(), response));
    }

    @GetMapping("/detail")
    public ResponseEntity<ApiResponse> getMagazineDetail(@RequestParam Long magazineId) {
        val response = magazineService.getMagazineDetail(magazineId);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_MAGAZINE_DETAIL.getMessage(), response));
    }

    @GetMapping("/scraps")
    public ResponseEntity<ApiResponse> getMagazineScraps(Principal principal) {
        val userId = AuthCommon.getUser(principal);
        val response = magazineService.getMagazineScraps(userId);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_MAGAZINE_SCRAP_LIST.getMessage(), response));
    }

    @GetMapping("/popular")
    public ResponseEntity<ApiResponse> getPopularMagazines(Principal principal) {
        val userId = AuthCommon.getUser(principal);
        val response = magazineService.getPopularMagazines(userId);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_MAIN_MAGAZINE_LIST.getMessage(), response));
    }

    @PostMapping("/scrap")
    public ResponseEntity<ApiResponse> magazineScrap(Principal principal, @RequestBody MagazineScrapRequestDTO request) {
        val userId = AuthCommon.getUser(principal);
        val response = magazineService.scrapMagazine(userId, request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_MAGAZINE_SCRAP.getMessage(), response));
    }
}
