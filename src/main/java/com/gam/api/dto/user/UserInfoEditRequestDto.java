package com.gam.api.dto.user;

import lombok.Builder;

import java.util.ArrayList;

@Builder
public record UserInfoEditRequestDto (
        String userInfo,
        String userDetail,
        ArrayList<String> tag
){
}
