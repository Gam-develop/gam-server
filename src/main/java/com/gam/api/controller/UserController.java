package com.gam.api.controller;

import com.gam.api.common.ApiResponse;
import com.gam.api.dto.user.UserInfoEditRequestDto;
import com.gam.api.dto.user.UserScrapRequestDto;
import com.gam.api.dto.user.UserScrapResponseDto;
import com.gam.api.entity.User;
import com.gam.api.entity.UserScrap;
import com.gam.api.repository.UserRepository;
import com.gam.api.service.UserService;
import com.gam.api.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


import static com.gam.api.common.message.ExceptionMessage.NOT_FOUND_USER;
import static com.gam.api.common.message.ResponseMessage.SUCCESS_USER_DELETE_SCRAP;
import static com.gam.api.common.message.ResponseMessage.SUCCESS_USER_SCRAP;
import static java.util.Objects.isNull;

@Controller
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/scrap")
    ResponseEntity<ApiResponse> scrapUser(Principal principal, @RequestBody UserScrapRequestDto request){
        val userId = getUser(principal);
        val response = userService.scrapUser(userId, request);
        if (response.userScrap()){
            return ResponseEntity.ok(ApiResponse.success(SUCCESS_USER_SCRAP.getMessage(),response));
        }
        return ResponseEntity.ok(ApiResponse.success(SUCCESS_USER_DELETE_SCRAP.getMessage(),response));
    }

    private Long getUser(Principal principal){
        if (isNull(principal.getName())) {
            throw new IllegalArgumentException(NOT_FOUND_USER.getName());
        }
        return Long.valueOf(principal.getName());
    }
}
