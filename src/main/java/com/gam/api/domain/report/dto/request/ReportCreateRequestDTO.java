package com.gam.api.domain.report.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record ReportCreateRequestDTO (
        Long targetUserId,
        @NotBlank
        @NotNull
        String content,
        Long workId
){
}
