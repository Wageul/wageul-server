package com.wageul.wageul_server.user.service;

import com.wageul.wageul_server.common.response.GoogleAccountProfileResponse;
import com.wageul.wageul_server.user.UserEntity;
import com.wageul.wageul_server.user.domain.User;
import com.wageul.wageul_server.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findOrCreate(GoogleAccountProfileResponse profile) {
        return userRepository.findByEmail(profile.email())
                .orElseGet(() -> userRepository.save(User.builder()
                        .email(profile.email())
                        .name(profile.name())
                        .profileImg(profile.picture())
                        .build()));
    }
}
