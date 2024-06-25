package com.wageul.wageul_server.user.domain;

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
    private String nationality;
    private String introduce;

    public User update(UserUpdate userUpdate) {
        return User.builder()
                .id(id)
                .email(email)
                .profileImg(userUpdate.getProfileImg())
                .name(userUpdate.getName())
                .nationality(userUpdate.getNationality())
                .introduce(userUpdate.getIntroduce())
                .build();
    }
}
