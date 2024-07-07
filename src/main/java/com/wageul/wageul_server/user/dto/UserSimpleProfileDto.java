package com.wageul.wageul_server.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSimpleProfileDto {

	private final long id;
	private final String profileImg;
	private final String name;
}
