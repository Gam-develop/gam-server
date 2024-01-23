package com.gam.api.dto.work.request;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;


public record WorkEditRequestDTO(
        Long workId,
        @NotBlank
        String image,
        @NotBlank
        String title,
        String detail
) {
}
