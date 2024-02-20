package com.gam.api.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "com.gam.api.domain")
@Configuration
public class FeignConfig {
}
