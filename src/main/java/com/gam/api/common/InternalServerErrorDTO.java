package com.gam.api.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InternalServerErrorDTO {
    private String header;
    private String httpMethod;
    private String URL;
    private String message;
    private String errorClass;
    private String dateTime;

    @Builder
    private InternalServerErrorDTO(String header, String httpMethod, String URL, String message,
                                   String errorClass, String dateTime) {
        this.header = header;
        this.httpMethod = httpMethod;
        this.URL = URL;
        this.message = message;
        this.errorClass = errorClass;
        this.dateTime = dateTime;
    }

    public static InternalServerErrorDTO of(String header, String httpMethod, String URL, String message,
                                            String errorClass, LocalDateTime nowDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String now = nowDateTime.format(formatter);

        return InternalServerErrorDTO.builder()
                .header(header)
                .httpMethod(httpMethod)
                .URL(URL)
                .message(message)
                .errorClass(errorClass)
                .dateTime(now)
                .build();
    }

    public static String extractHeaders(HttpServletRequest request) {
        StringBuilder headers = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headers.append(headerName).append(": ").append(headerValue).append("    ");
        }
        return headers.toString();
    }
}
