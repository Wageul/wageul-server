package com.wageul.wageul_server.bookmark.domain;

import java.time.LocalDateTime;

import com.wageul.wageul_server.experience.domain.Experience;
import com.wageul.wageul_server.user.domain.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Bookmark {

	private final Long id;
	private final User user;
	private final Experience experience;
	private final LocalDateTime createdAt;
}
