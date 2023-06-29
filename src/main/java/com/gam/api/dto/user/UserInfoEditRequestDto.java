package com.gam.api.dto.user;

import java.util.ArrayList;

public record UserInfoEditRequestDto(
        String userInfo,
        String userDetail,
        ArrayList<String> tags
) {
}
