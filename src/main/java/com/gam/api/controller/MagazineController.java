package com.gam.api.controller;

import com.gam.api.common.ApiResponse;
import com.gam.api.common.message.ResponseMessage;
import com.gam.api.dto.magazine.request.MagazineScrapRequestDTO;
import com.gam.api.entity.GamUserDetails;
import com.gam.api.service.magazine.MagazineService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/magazine")
public class MagazineController {
    private final MagazineService magazineService;

    @ApiOperation(value = "매거진 - 발견")
    @GetMapping("")
    public ResponseEntity<ApiResponse> getMagazines(@AuthenticationPrincipal GamUserDetails userDetails) {
        val response = magazineService.getMagazines(userDetails.getId());
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_MAGAZINE_LIST.getMessage(), response));
    }

    @ApiOperation(value = "매거진 - 세부")
    @GetMapping("/detail")
    public ResponseEntity<ApiResponse> getMagazineDetail(@RequestParam Long magazineId) {
        val response = magazineService.getMagazineDetail(magazineId);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_MAGAZINE_DETAIL.getMessage(), response));
    }

    @ApiOperation(value = "스크랩 - 매거진 스크랩 뷰")
    @GetMapping("/scraps")
    public ResponseEntity<ApiResponse> getMagazineScraps(@AuthenticationPrincipal GamUserDetails userDetails) {
        val response = magazineService.getMagazineScraps(userDetails.getId());
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_MAGAZINE_SCRAP_LIST.getMessage(), response));
    }

    @ApiOperation(value = "메인 홈 - 영감 매거진")
    @GetMapping("/popular")
    public ResponseEntity<ApiResponse> getPopularMagazines(@AuthenticationPrincipal GamUserDetails userDetails) {
        val response = magazineService.getPopularMagazines(userDetails.getId());
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_MAIN_MAGAZINE_LIST.getMessage(), response));
    }

    @ApiOperation(value = "스크랩 - 매거진 스크랩, 스크랩 취소")
    @PostMapping("/scrap")
    public ResponseEntity<ApiResponse> magazineScrap(@AuthenticationPrincipal GamUserDetails userDetails, @RequestBody MagazineScrapRequestDTO request) {
        val response = magazineService.scrapMagazine(userDetails.getId(), request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_MAGAZINE_SCRAP.getMessage(), response));
    }

    @ApiOperation(value = "매거진 검색")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchMagazine(@RequestParam String keyword) {
        val response = magazineService.searchMagazine(keyword);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_SEARCH_MAGAZINE.getMessage(),response));
    }

}
