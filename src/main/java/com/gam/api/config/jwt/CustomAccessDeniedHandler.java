package com.gam.api.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gam.api.common.ApiResponse;
import com.gam.api.common.message.ExceptionMessage;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException accessDeniedException) throws IOException {

        val objectMapper = new ObjectMapper();
        String exceptionMessage;

        if (httpServletRequest.getRequestURI().contains("manager")) {
            exceptionMessage = ExceptionMessage.NOT_ADMIN_USER.getMessage();
        }
        else exceptionMessage = ExceptionMessage.PROFILE_UNCOMPLETED_USER.getMessage();

        val jsonResponse = objectMapper.writeValueAsString(
                    ApiResponse.fail(exceptionMessage)
                );

        httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().write(jsonResponse);
    }
}