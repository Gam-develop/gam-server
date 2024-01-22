package com.gam.api.dto.user.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public record UserDeleteAccountRequestDTO(
        @NotEmpty(message = "delete account reason은 null일 수 없습니다, 하나 이상의 delete account reason을 선택 해 주세요.")
        int[] deleteAccountReasons,
        String directInput
){
}
