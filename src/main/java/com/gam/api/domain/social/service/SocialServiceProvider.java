package com.gam.api.domain.social.service;

import com.gam.api.domain.social.apple.AppleSocialService;
import com.gam.api.domain.social.kakao.KakaoSocialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.gam.api.domain.user.entity.ProviderType;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SocialServiceProvider {

    private static final Map<ProviderType, SocialService> socialServiceMap = new HashMap<>();

    private final KakaoSocialService kakaoSocialService;
    private final AppleSocialService appleSocialService;

    @PostConstruct
    void initializeSocialServiceMap() {
        socialServiceMap.put(ProviderType.KAKAO, kakaoSocialService);
        socialServiceMap.put(ProviderType.APPLE, appleSocialService);
    }

    public SocialService getSocialService(ProviderType providerType) {
        return socialServiceMap.get(providerType);
    }
}