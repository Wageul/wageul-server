package com.wageul.wageul_server.s3_image.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExImageDto {
    private Long id;
    private String image;

    public ExImageDto withUrl(String imageUrl) {
        return ExImageDto.builder()
                .id(id)
                .image(imageUrl)
                .build();
    }
}
