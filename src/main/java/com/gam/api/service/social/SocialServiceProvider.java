package com.gam.api.service.social;

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