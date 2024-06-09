package com.gam.api.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class GamConfig {
    @Value("${gam.app.version}")
    private String appVersion;

    @Value("${gam.url.intro}")
    private String introUrl;

    @Value("${gam.url.agreement}")
    private String agreementUrl;

    @Value("${gam.url.policy}")
    private String policyUrl;

    @Value("${gam.url.makers}")
    private String makersUrl;

    @Value("${gam.url.magazineBase}")
    private String magaineBaseUrl;

    @Value("${gam.url.openSourceUrl}")
    private String openSourceUrl;
}
