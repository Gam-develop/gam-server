package com.gam.api.domain.report.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record ReportCreateRequestDTO (
        Long targetUserId,
        @NotBlank(message = "신고내용은 빈스트링일 수 없습니다.")
        @NotNull(message = "신고내용은 null일 수 없습니다.")
        String content
){
}
