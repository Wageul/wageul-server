package com.wageul.wageul_server.participation.domain;

import java.time.LocalDateTime;

import com.wageul.wageul_server.experience.domain.Experience;
import com.wageul.wageul_server.user.domain.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Participation {
	private long id;
	private final User user;
	private final Experience experience;
	private final LocalDateTime createdAt;

	public static Participation from(User user, Experience experience) {
		return Participation.builder()
			.user(user)
			.experience(experience)
			.build();
	}
}
