package com.gam.api.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class UrlConfig {

    @Value("${magazine.baseUrl}")
    private String baseUrl;
}
