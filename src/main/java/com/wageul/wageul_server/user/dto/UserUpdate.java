package com.wageul.wageul_server.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserUpdate {

    private String name;
    private String nationality;
    private String introduce;

    public UserUpdate() {}

    @Builder
    public UserUpdate(String name, String nationality, String introduce) {
        this.name = name;
        this.nationality = nationality;
        this.introduce = introduce;
    }
}
