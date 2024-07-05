package com.wageul.wageul_server.participation.dto;

import java.util.List;

import com.wageul.wageul_server.user.domain.User;
import com.wageul.wageul_server.user.dto.UserSimpleProflieDto;

import lombok.Getter;

@Getter
public class ExperienceParticipationResponse {

	private final long experienceId;
	private final List<UserSimpleProflieDto> userSimpleProflieList;

	public ExperienceParticipationResponse(long experienceId, List<User> users) {
		this.experienceId = experienceId;
		this.userSimpleProflieList = users.stream().map(user -> {
			return UserSimpleProflieDto.builder()
				.id(user.getId())
				.profileImg(user.getProfileImg())
				.name(user.getName())
				.build();
		}).toList();
	}
}
