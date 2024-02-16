package com.gam.api.config.jwt;

import com.gam.api.common.ApiResponse;
import com.gam.api.common.exception.AuthException;
import javax.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.val;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch(AuthException | EntityNotFoundException e) {
            val objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(ApiResponse.fail(e.getMessage()));

            if(e instanceof AuthException) {
                httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            } else if(e instanceof EntityNotFoundException) {
                httpServletResponse.setStatus(HttpStatus.NOT_FOUND.value());
            }
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.getWriter().write(jsonResponse);
        }
    }
}