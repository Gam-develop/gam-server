package com.gam.api.domain.user.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public record UserOnboardRequestDTO(
        @NotBlank
        @NotEmpty
        String userName,
        @NotBlank
        @NotEmpty
        String info,
        @NotEmpty(message = "tags는 null일 수 없습니다, 하나 이상의 tag를 선택 해 주세요.")
        int[] tags
) {
}
