package com.gam.api.domain.util.controller;

import com.gam.api.common.ApiResponse;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UtilController {
    private final Environment env;

    @ApiOperation(value = "heal-check 컨트롤러")
    @GetMapping("/health")
    public ResponseEntity<ApiResponse> test() {
        return ResponseEntity.ok(ApiResponse.success("Hello gam Server!"));
    }

    @GetMapping(value = "/robots.txt")
    @ResponseBody
    public void robotsBlock(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.getWriter().write("User-agent: *\nDisallow: /\n");
        } catch (IOException e) {
            log.info("exception", e);
        }
    }
}