package com.gam.api.domain.block.dto.response;

import com.gam.api.domain.user.entity.User;
import lombok.Builder;

@Builder
public record BlockResponseDTO(
        Long targetUserId,
        String targetUserName,
        boolean blockStatus
){
    public static BlockResponseDTO of(User targetUser, boolean blockStatus) {
        return BlockResponseDTO.builder()
                .targetUserId(targetUser.getId())
                .targetUserName(targetUser.getUserName())
                .blockStatus(blockStatus)
                .build();
    }

}
