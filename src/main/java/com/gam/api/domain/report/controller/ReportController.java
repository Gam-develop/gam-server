package com.gam.api.domain.report.controller;

import com.gam.api.common.ApiResponse;
import com.gam.api.common.message.ResponseMessage;
import com.gam.api.domain.report.dto.request.ReportCreateRequestDTO;
import com.gam.api.domain.report.service.ReportService;
import com.gam.api.domain.user.entity.GamUserDetails;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/report")
public class ReportController {
    private final ReportService reportService;

    @ApiOperation(value = "유저 신고")
    @PostMapping("")
    public ResponseEntity<ApiResponse> createReport(@AuthenticationPrincipal GamUserDetails userDetails,
                                                    @Valid @RequestBody ReportCreateRequestDTO request){
     reportService.createReport(userDetails.getId(), request);
     return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_REPORT_USER.getMessage(), null));
    }
}
