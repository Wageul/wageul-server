package com.wageul.wageul_server.s3_image.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProfileUpdate {

    private final String profile;

    @Builder
    public ProfileUpdate(String profile) {
        this.profile = profile;
    }
}
