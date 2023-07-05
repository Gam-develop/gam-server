package com.gam.api.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {

    /** auth **/
    SUCCESS_SIGN_UP("회원 가입 성공"),
    SUCCESS_LOGIN_UP("로그인 성공"),
    SUCCESS_GET_REFRESH_TOKEN("토큰 재발급 성공"),


    /** s3 **/
    SUCCESS_GET_PRESIGNED_URL("단일 이미지 업로드 URL 가져오기 성공"),
    SUCCESS_GET_PRESIGNED_URLS("다중 이미지 업로드 URL 가져오기 성공"),
    SUCCESS_DELETE_IMAGE("이미지 삭제 성공"),

    /** user **/
    SUCCESS_USER_SCRAP("유저 스크랩 성공"),
    SUCCESS_USER_DELETE_SCRAP("유저 스크랩 취소 성공"),
    SUCCESS_UPDATE_EXTERNAL_LINK("유저 외부 링크 수정 성공"),
    SUCCESS_GET_MY_PROFILE("내 프로필 보기 성공"),

    /** work **/
    SUCCESS_CREATE_WORK("작업물 생성 성공"),
    SUCCESS_DELETE_WORK("작업물 삭제 성공");

    private final String message;
}

