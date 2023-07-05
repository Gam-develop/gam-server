package com.gam.api.controller;

import com.gam.api.common.message.*;
import com.gam.api.common.ApiResponse;
import com.gam.api.common.message.ExceptionMessage;
import com.gam.api.dto.user.request.UserExternalLinkRequestDto;
import com.gam.api.dto.user.request.UserProfileUpdateRequestDto;
import com.gam.api.dto.user.request.UserScrapRequestDto;
import com.gam.api.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


import static java.util.Objects.isNull;

@Controller
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/scrap")
    ResponseEntity<ApiResponse> scrapUser(Principal principal, @RequestBody  UserScrapRequestDto request){
        val userId = getUser(principal);
        val response = userService.scrapUser(userId, request);
        if (response.userScrap()){
            return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_USER_SCRAP.getMessage(),response));
        }
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_USER_DELETE_SCRAP.getMessage(),response));
    }

    @PatchMapping("/link")
    ResponseEntity<ApiResponse> updateExternalLink(Principal principal, @RequestBody UserExternalLinkRequestDto request){
        val userId = getUser(principal);
        val response = userService.updateExternalLink(userId, request);

        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_UPDATE_EXTERNAL_LINK.getMessage(), response));
    }

    @PatchMapping("/introduce")
    ResponseEntity<ApiResponse> updateMyProfile(Principal principal, @RequestBody UserProfileUpdateRequestDto request){
        val userId = getUser(principal);
        userService.updateMyProfile(userId, request);

        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_UPDATE_EXTERNAL_LINK.getMessage(), "냐"));
    }

    private Long getUser(Principal principal){
        if (isNull(principal.getName())) {
            throw new IllegalArgumentException(ExceptionMessage.NOT_FOUND_USER.getMessage());
        }
        return Long.valueOf(principal.getName());
    }
}
