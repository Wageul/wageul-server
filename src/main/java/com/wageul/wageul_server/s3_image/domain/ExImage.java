package com.wageul.wageul_server.s3_image.domain;

import com.wageul.wageul_server.experience.domain.Experience;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExImage {

	private final Long id;
	private final String image;
	private final Experience experience;
}
