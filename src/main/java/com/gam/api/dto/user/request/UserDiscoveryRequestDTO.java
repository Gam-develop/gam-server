package com.gam.api.dto.user.request;

import javax.validation.constraints.NotEmpty;

public record UserDiscoveryRequestDTO(
        @NotEmpty(message = "tags는 null일 수 없습니다, 하나 이상의 tag를 선택 해 주세요.")
        int[] tags
) {
}
