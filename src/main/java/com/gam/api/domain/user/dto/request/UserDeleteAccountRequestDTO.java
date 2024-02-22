package com.gam.api.domain.user.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public record UserDeleteAccountRequestDTO(
        @NotEmpty(message = "delete account reason은 null일 수 없습니다, 하나 이상의 delete account reason을 선택 해 주세요.")
        List<Integer> deleteAccountReasons,
        String directInput
){
}
