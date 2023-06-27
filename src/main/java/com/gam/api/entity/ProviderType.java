package com.gam.api.entity;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public enum ProviderType {
    @Enumerated(EnumType.STRING)
    kakao, naver, google
}
