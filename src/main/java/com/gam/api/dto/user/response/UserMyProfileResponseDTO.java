package com.gam.api.dto.user.response;


import com.gam.api.entity.User;
import lombok.Builder;

@Builder
public record UserMyProfileResponseDTO(
        Long userId,
        String userName,
        String info,
        String detail,
        String email,
        int[] userTag
) {
    public static UserMyProfileResponseDTO of(User user){
        return UserMyProfileResponseDTO.builder()
                .userId(user.getId())
                .userName(user.getUserName())
                .info(user.getInfo())
                .detail(user.getDetail())
                .email(user.getEmail())
                .userTag(user.getTags() == null ? new int[0] : user.getTags())
                .build();
    }
}
