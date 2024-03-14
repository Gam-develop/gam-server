package com.gam.api.domain.work.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record WorkCreateRequestDTO(
        @NotNull(message = "Work image는 null일 수 없습니다.")
        @NotBlank(message = "Work image는 빈스트링일 수 없습니다.")
        String image,
        @NotNull(message = "Work title은 null일 수 없습니다.")
        @NotBlank(message = "Work title은 빈스트링일 수 없습니다.")
        @Size(min = 1, max = 18, message = "Work title은 18자를 넘을 수 없습니다.")
        String title,
        @NotNull(message = "Work detail은 null일 수 없습니다.")
        @NotBlank(message = "Work detail은 빈스트링일 수 없습니다.")
        String detail) {
}
