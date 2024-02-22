package com.gam.api.domain.block.controller;

import com.gam.api.common.ApiResponse;
import com.gam.api.common.message.ResponseMessage;
import com.gam.api.domain.block.dto.request.BlockRequestDTO;
import com.gam.api.domain.block.service.BlockService;
import com.gam.api.domain.user.entity.GamUserDetails;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "유저를 차단합니다.")
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
