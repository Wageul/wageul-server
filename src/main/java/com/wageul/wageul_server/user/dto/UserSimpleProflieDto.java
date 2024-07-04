package com.wageul.wageul_server.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSimpleProflieDto {

	private final String profileImg;
	private final String name;
}
