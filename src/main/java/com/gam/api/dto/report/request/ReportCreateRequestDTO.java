package com.gam.api.dto.report.request;

import com.gam.api.entity.Report;

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
