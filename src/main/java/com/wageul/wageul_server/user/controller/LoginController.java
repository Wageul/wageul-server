package com.wageul.wageul_server.user.controller;

import com.wageul.wageul_server.user.dto.LoginResponse;
import com.wageul.wageul_server.user.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@Controller
@RestController
@RequiredArgsConstructor
public class LoginController {

    @Value("${google.client-id}")
    private String clientId;
    @Value("${google.redirect-uri}")
    private String redirectUri;

    private final LoginService loginService;

    @GetMapping("/api/google/login")
    public void googleLoginRedirect(HttpServletResponse response) throws IOException {
        String redirectUrl = "https://accounts.google.com/o/oauth2/v2/auth?client_id="
                    + clientId + "&redirect_uri=" + redirectUri
                    + "&response_type=code" + "&scope=email profile";
        response.sendRedirect(redirectUrl);
    }

    @GetMapping("/api/google/login/code")
    public ResponseEntity<LoginResponse> googleLoginProcess(@RequestParam(name = "code") String code) {
        LoginResponse loginResponse = loginService.loginByGoogle(code);
        return ResponseEntity.ok().body(loginResponse);
    }
}
