package com.gam.api.domain.user.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public record UserOnboardRequestDTO(
        @Size(min= 1, max= 15, message = "userName의 길이는 1글자 이상 15글자 이하여야 합니다.")
        String userName,
        @NotBlank(message = "info는 \"\"일 수 없습니다.")
        @NotEmpty(message = "info는 null일 수 없습니다.")
        String info,
        @NotEmpty(message = "tags는 null일 수 없습니다, 하나 이상의 tag를 선택 해 주세요.")
        int[] tags
) {
}
