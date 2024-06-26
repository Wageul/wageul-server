package com.wageul.wageul_server.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {
	private long id;
	private String email;
	private String name;
	private String username;
}
