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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

import static com.gam.api.common.message.ExceptionMessage.NOT_FOUND_USER;
import static com.gam.api.common.message.ResponseMessage.SUCCESS_USER_DELETE_SCRAP;
import static com.gam.api.common.message.ResponseMessage.SUCCESS_USER_SCRAP;
import static java.util.Objects.isNull;

@Controller
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final UserRepository userRepository;
    @PostMapping("/scrap")
    ResponseEntity<ApiResponse> postUserScrap(Principal principal, @RequestBody UserScrapRequestDto request){
        Long userId = getUser(principal);
        UserScrapResponseDto response = userService.postUserScrap(userId, request);
        if (response.userScrap()){
            return ResponseEntity.ok(ApiResponse.success(SUCCESS_USER_SCRAP.getMessage(),response));
        }return ResponseEntity.ok(ApiResponse.success(SUCCESS_USER_DELETE_SCRAP.getMessage(),response));
    }


    private Long getUser(Principal principal){
        if (isNull(principal.getName()))
            throw new IllegalArgumentException(NOT_FOUND_USER.getName());
        return Long.valueOf(principal.getName());
    }
}
