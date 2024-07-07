package com.wageul.wageul_server.s3_image.service;

import com.wageul.wageul_server.oauth2.AuthorizationUtil;
import com.wageul.wageul_server.s3_image.dto.ProfileImageRequest;
import com.wageul.wageul_server.s3_image.dto.ProfileImageResponse;
import com.wageul.wageul_server.user.domain.User;
import com.wageul.wageul_server.user.dto.UserUpdate;
import com.wageul.wageul_server.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileImageService {

    private final UserRepository userRepository;
    private final AuthorizationUtil authorizationUtil;

    public String getMyProfileImage() {
        // 사용자 정보 가져오기
        User user = userRepository.findById(authorizationUtil.getLoginUserId())
                .orElseThrow(() -> new RuntimeException("NO USER"));
        // 프로필 이미지 주소 가져오기
        return user.getProfileImg();
    }

    public User upload(String profileImageDir) {
        User user = userRepository.findById(authorizationUtil.getLoginUserId())
                .orElseThrow(() -> new RuntimeException("NO USER"));

        UserUpdate userUpdateProfile = UserUpdate.builder()
                .profileImg(profileImageDir)
                .build();

        user = user.update(userUpdateProfile);
        return userRepository.save(user);
    }
}
