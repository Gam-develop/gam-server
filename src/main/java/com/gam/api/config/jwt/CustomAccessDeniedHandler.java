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

        val exceptionMessage = determineExceptionMessage(httpServletRequest.getRequestURI());
        val jsonResponse = objectMapper.writeValueAsString(
                    ApiResponse.fail(exceptionMessage)
                );

        httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().write(jsonResponse);
    }

    private String determineExceptionMessage(String requestUri) {
        if (requestUri.contains("admin")) {
            return ExceptionMessage.NOT_ADMIN_USER.getMessage();
        } else if(requestUri.contains("/magazine/detail")) {
            return ExceptionMessage.PORTFOLIO_UNCOMPLETED_USER.getMessage();
        } else if(requestUri.contains("work") || requestUri.contains("user") || requestUri.contains("magazine")) {
            return ExceptionMessage.WITHDRAWAL_USER.getMessage();
        }
        return ExceptionMessage.SECURITY_FILTER_EXCPETION.getMessage();
    }
}