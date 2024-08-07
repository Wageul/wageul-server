package com.wageul.wageul_server.user.domain;

import java.time.LocalDateTime;

import com.wageul.wageul_server.s3_image.dto.ProfileUpdate;
import com.wageul.wageul_server.user.dto.UserUpdate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
    private long id;
    private final String email;
    private String profileImg;
    private String name;
    private String username;
    private String nationality;
    private String introduce;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User update(UserUpdate userUpdate) {
        return User.builder()
                .id(id)
                .email(email)
                .profileImg(profileImg)
                .name(userUpdate.getName() == null ? name : userUpdate.getName())
                .username(username)
                .nationality(userUpdate.getNationality() == null ? nationality : userUpdate.getNationality())
                .introduce(userUpdate.getIntroduce() == null ? introduce : userUpdate.getIntroduce())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public User withProfileUrl(String profileUrl) {
        return User.builder()
                .id(id)
                .email(email)
                .profileImg(profileUrl)
                .name(name)
                .username(username)
                .nationality(nationality)
                .introduce(introduce)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public User updateProfile(ProfileUpdate userUpdateProfile) {
        return User.builder()
                .id(id)
                .email(email)
                .profileImg(userUpdateProfile.getProfile())
                .name(name)
                .username(username)
                .nationality(nationality)
                .introduce(introduce)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
