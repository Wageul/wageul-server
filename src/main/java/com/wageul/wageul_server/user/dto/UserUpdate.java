package com.wageul.wageul_server.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserUpdate {

    private final String profileImg;
    private final String name;
    private final String nationality;
    private final String introduce;

    @Builder
    public UserUpdate(
        @JsonProperty("profileImg") String profileImg,
        @JsonProperty("name") String name,
        @JsonProperty("nationality") String nationality,
        @JsonProperty("introduce") String introduce) {
        this.profileImg = profileImg;
        this.name = name;
        this.nationality = nationality;
        this.introduce = introduce;
    }
}
