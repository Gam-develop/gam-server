package com.gam.api.domain.social.kakao.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoUserResponse(String id, String connected_at, boolean setPrivacyInfo, KakaoUserAccountResponse kakao_account) {}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record KakaoUserAccountResponse(boolean has_age_range, boolean age_range_needs_agreement, boolean has_gender, boolean gender_needs_agreement) {}