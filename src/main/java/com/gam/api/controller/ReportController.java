package com.gam.api.controller;

import com.gam.api.common.ApiResponse;
import com.gam.api.common.message.ResponseMessage;
import com.gam.api.dto.report.request.ReportCreateRequestDTO;
import com.gam.api.entity.GamUserDetails;
import com.gam.api.service.report.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/report")
public class ReportController {
    private final ReportService reportService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> createReport(@Valid @RequestBody ReportCreateRequestDTO request){
     reportService.createReport(request);
     return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_REPORT_USER.getMessage(), null));
    }
}
