package com.wageul.wageul_server.user.domain;

import java.time.LocalDateTime;

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
                .profileImg(userUpdate.getProfileImg())
                .name(userUpdate.getName())
                .username(username)
                .nationality(userUpdate.getNationality())
                .introduce(userUpdate.getIntroduce())
                .createdAt(createdAt)
                .build();
    }
}
