package com.gam.api.config.jwt;

import com.gam.api.common.exception.AuthException;
import com.gam.api.common.message.ExceptionMessage;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenManager jwtTokenManager;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        val uri = request.getRequestURI();

        if ((uri.startsWith("/api/v1")) && !uri.contains("social")
                && !uri.contains("s3") && !uri.contains("url")
                && !uri.contains("/name/check")) {
            val token = resolveToken(request);
            val isTokenAvailable = checkJwtAvailable(token);

            if (Objects.isNull(token)) {
                throw new AuthException(ExceptionMessage.EMPTY_TOKEN.getMessage(), HttpStatus.BAD_REQUEST);
            }

            if (!isTokenAvailable) {
                throw new AuthException(ExceptionMessage.INVALID_TOKEN.getMessage(), HttpStatus.UNAUTHORIZED);
            }

            val auth = jwtTokenManager.getAuthentication(token);
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }

    private boolean checkJwtAvailable (String jwtToken) {
        return jwtToken != null && jwtTokenManager.verifyAuthToken(jwtToken);
    }

    private String resolveToken(HttpServletRequest request) {
        val headerAuth = request.getHeader("Authorization");
        return (StringUtils.hasText(headerAuth)) ? headerAuth : null;
    }
}