package com.gam.api.dto.user.response;

import com.gam.api.entity.User;
import com.gam.api.entity.UserStatus;
import lombok.Builder;

@Builder
public record UserStatusResponseDTO(
        UserStatus userStatus,
        int magazineCount
){
    public static UserStatusResponseDTO of (User user) {
        return UserStatusResponseDTO.builder()
                .userStatus(user.getUserStatus())
                .magazineCount(user.getMagazineViewCount())
                .build();
    }
}
