package com.wageul.wageul_server.participation.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExperienceParticipationResponse {

	private final long experienceId;
	private final List<ParticipationUserResponse> userSimpleProflieList;
}
