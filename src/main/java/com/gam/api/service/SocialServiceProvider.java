package com.gam.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.gam.api.entity.ProviderType;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SocialServiceProvider {

    private static final Map<ProviderType, SocialService> socialServiceMap = new HashMap<>();

    private final KakaoSocialService kakaoSocialService;

    @PostConstruct
    void initializeSocialServiceMap() {
        socialServiceMap.put(ProviderType.KAKAO, kakaoSocialService);
    }

    public SocialService getSocialService(ProviderType providerType) {
        return socialServiceMap.get(providerType);
    }
}