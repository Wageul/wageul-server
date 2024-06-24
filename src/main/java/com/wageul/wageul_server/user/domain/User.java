package com.wageul.wageul_server.user.domain;

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
    private String language;
    private int ageRange;
    private String introduce;
}
