package com.gam.api.config;

import com.gam.api.config.jwt.CustomAccessDeniedHandler;
import com.gam.api.config.jwt.JwtAuthenticationFilter;
import com.gam.api.config.jwt.JwtExceptionFilter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtExceptionFilter jwtExceptionFilter;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic().disable()
                .csrf().disable()
                .exceptionHandling()
                .and()
                    .cors().configurationSource(corsConfigurationSource())
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeHttpRequests()
                    .antMatchers("/v3/api-docs/**", "/swagger-ui.html", "/webjars/swagger-ui/**", "/swagger-ui/**", "/api/v1/social/**", "/api/v1/work","/api/v1/user/name/check").permitAll()
                .and()
                    .authorizeHttpRequests()
                    .antMatchers("/api/v1/s3/**", "/api/v1/work/**", "/api/v1/user/**", "/api/v1/magazine/**").hasAuthority("PERMITTED")
                .and()
                    .authorizeHttpRequests()
                    .antMatchers("/api/v1/admin/**").hasRole("ADMIN")
                .and()
                    .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        val configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(false);

        val source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
