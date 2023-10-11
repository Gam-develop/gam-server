package com.gam.api.controller;

import com.gam.api.common.ApiResponse;
import com.gam.api.common.message.ResponseMessage;
import com.gam.api.dto.block.request.BlockRequestDTO;
import com.gam.api.entity.GamUserDetails;
import com.gam.api.service.block.BlockService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/block")
public class BlockController {
    private final BlockService blockService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> createBlock(@AuthenticationPrincipal GamUserDetails userDetails, @RequestBody BlockRequestDTO request) {
        val userId = userDetails.getId();
        val response = blockService.createBlock(userId, request);

        if (response.blockStatus()) {
            return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_CREATE_BLOCK.getMessage()));
        }
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_CANCEL_BLOCK.getMessage()));
        }

}
