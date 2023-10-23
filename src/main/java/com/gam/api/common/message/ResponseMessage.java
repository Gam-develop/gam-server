package com.gam.api.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {

    /** auth **/
    SUCCESS_SIGN_UP("회원 가입 성공"),
    SUCCESS_LOGIN("로그인 성공"),
    SUCCESS_LOGOUT("로그아웃 성공"),
    SUCCESS_GET_REFRESH_TOKEN("토큰 재발급 성공"),


    /** s3 **/
    SUCCESS_GET_PRESIGNED_URL("단일 이미지 업로드 URL 가져오기 성공"),
    SUCCESS_GET_PRESIGNED_URLS("다중 이미지 업로드 URL 가져오기 성공"),
    SUCCESS_DELETE_IMAGE("이미지 삭제 성공"),

    /** user **/
    SUCCESS_USER_NAME_DUPLICATE_CHECK("닉네임 중복 확인 성공"),
    SUCCESS_USER_ONBOARD("온보딩 성공"),
    SUCCESS_USER_SCRAP("유저 스크랩 성공"),
    SUCCESS_USER_DELETE_SCRAP("유저 스크랩 취소 성공"),
    SUCCESS_GET_MY_PROFILE("내 프로필 보기 성공"),
    SUCCESS_UPDATE_MY_PROFILE("유저 정보 수정 성공"),
    SUCCESS_GET_USER_PROFILE("유저 프로필 보기 성공"),
    SUCCESS_UPDATE_INSTAGRAM_LINK("인스타그램 링크 변경을 완료했습니다."),
    SUCCESS_UPDATE_BEHANCE_LINK("비헨스 링크 변경을 완료했습니다."),
    SUCCESS_UPDATE_NOTION_LINK("노션 링크 변경을 완료했습니다."),
    SUCCESS_GET_POPULAR_USER("홈-인기디자이너 갖고 오기 성공"),
    SUCCESS_DISCOVERY_GET_USERS("둘러보기-발견에서 유저들의 정보를 갖고왔습니다."),
    SUCCESS_GET_USER_SCRAPS("스크랩한 유저들의 정보를 갖고 왔습니다."),

    /** work **/
    SUCCESS_CREATE_WORK("작업물 생성 성공"),
    SUCCESS_DELETE_WORK("작업물 삭제 성공"),
    SUCCESS_UPDATE_WORK("유저 작업물 수정이 완료 됐습니다."),
    SUCCESS_UPDATE_FIRST_WORK("대표 작업물 설정 성공"),

    /** magazine **/
    SUCCESS_GET_MAGAZINE_LIST("매거진 목록 가져오기 성공"),
    SUCCESS_GET_MAIN_MAGAZINE_LIST("영감 매거진 목록 가져오기 성공"),
    SUCCESS_GET_MAGAZINE_SCRAP_LIST("매거진 스크랩 목록 가져오기 성공"),
    SUCCESS_GET_MAGAZINE_DETAIL("매거진 상세 가져오기 성공"),
    SUCCESS_MAGAZINE_SCRAP("매거진 스크랩 성공"),
    SUCCESS_GET_PROTFOLIO_LIST("포트폴리오 상세보기 성공"),
    SUCCESS_SEARCH_MAGAZINE("매거진 검색 성공"),
    SUCCESS_DELETE_MAGAZINE("매거진 삭제 성공"),

    /** search **/
    SUCCESS_SEARCH_USE_WORKS("작업물, 유저 검색 성공"),

    /** url **/
    SUCCESS_GET_URL("주소 가져오기 성공"),

    /** admin **/
    SUCCESS_CREATE_MAGAZINE("매거진 생성 완료"),
    SUCCESS_EDIT_MAGAZINE("매거진 수정 완료"),

    /** report **/
    SUCCESS_REPORT_USER("유저 신고를 성공했습니다.");

    private final String message;
}

