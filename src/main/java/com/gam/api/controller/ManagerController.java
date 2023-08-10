package com.gam.api.controller;

import com.gam.api.common.ApiResponse;
import com.gam.api.common.message.ResponseMessage;
import com.gam.api.entity.GamUserDetails;
import com.gam.api.repository.UserRepository;
import com.gam.api.service.magazine.MagazineService;
import com.gam.api.service.manager.ManagerService;
import com.gam.api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/manager")
public class ManagerController {

    private final ManagerService managerService;
    private final UserService userService;

    @GetMapping("/magazine/list")
    public ResponseEntity<ApiResponse> getMagazines() {
        val response = managerService.getMagazines();
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_MAGAZINE_LIST.getMessage(), response));
    }
}
