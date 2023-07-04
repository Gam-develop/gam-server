package com.gam.api.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionMessage {
    EMPTY_TOKEN("빈 토큰입니다."),
    INVALID_TOKEN("유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN("만료된 토큰입니다."),
    INVALID_SIGNATURE("유효하지 않은 서명입니다."),
    INVALID_EXTENSION("유효하지 않은 확장자입니다.");

    private final String name;
}

