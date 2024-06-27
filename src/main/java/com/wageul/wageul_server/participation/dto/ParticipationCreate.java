package com.wageul.wageul_server.participation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ParticipationCreate {
	private final long experienceId;

	@Builder
	public ParticipationCreate(
		@JsonProperty("experienceId") long experienceId) {
		this.experienceId = experienceId;
	}
}
