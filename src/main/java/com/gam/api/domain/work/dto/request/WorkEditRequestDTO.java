package com.gam.api.domain.work.dto.request;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;


public record WorkEditRequestDTO(
        Long workId,
        @NotBlank(message = "작업물 이미지를 추가해주세요")
        String image,
        @NotBlank(message = "작업물 제목은 빈스트링 일 수 없습니다.")
        String title,
        String detail
) {
}
