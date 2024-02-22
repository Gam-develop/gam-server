package com.gam.api.domain.user.dto.response;

import com.gam.api.domain.user.entity.User;
import com.gam.api.domain.user.entity.UserStatus;
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
