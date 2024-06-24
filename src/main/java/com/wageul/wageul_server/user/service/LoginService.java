package com.wageul.wageul_server.user.service;

import com.wageul.wageul_server.common.GoogleClient;
import com.wageul.wageul_server.common.JwtTokenGenerator;
import com.wageul.wageul_server.common.response.GoogleAccountProfileResponse;
import com.wageul.wageul_server.user.domain.User;
import com.wageul.wageul_server.user.dto.LoginResponse;
import com.wageul.wageul_server.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserService userService;
    private final GoogleClient googleClient;
    private final JwtTokenGenerator jwtTokenGenerator;

    public LoginResponse loginByGoogle(String code) {
        final GoogleAccountProfileResponse profile = googleClient.getGoogleAccountProfile(code);
        final User user = userService.findOrCreate(profile);
        final String token = jwtTokenGenerator.generateToken(user.getId());
        log.info(user.toString());

        return new LoginResponse(user.getId(), token);
    }
}
