package com.gam.api.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {

    /** auth **/
    SUCCESS_SIGN_UP("회원 가입 성공"),
    SUCCESS_LOGIN_UP("로그인 성공"),
    SUCCESS_GET_REFRESH_TOKEN("토큰 재발급 성공"),

    /** auth **/
    SUCCESS_GET_PRESIGNED_URL("단일 이미지 업로드 URL 가져오기 성공"),
    SUCCESS_GET_PRESIGNED_URLS("다중 이미지 업로드 URL 가져오기 성공");

    private final String message;
}

