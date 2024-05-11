//package com.gam.api.domain.user.schedular;
//
//import com.gam.api.domain.user.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class UserSchedular {
//
//    private final UserService userService;
//
//    @Scheduled(fixedDelay = 100000)
//    public void userAccountDisabled() {
//        userService.deactivateUserAccount();
//    }
//}
