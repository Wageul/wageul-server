package com.wageul.wageul_server.experience.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExperienceNoWriterResponse {

	private final long id;
	private final String title;
	private final String location;
	private final LocalDateTime datetime;
	private final String content;
	private final LocalTime duration;
	private final int cost;
	private final String contact;
	private final int limitMember;
	private final long writerId;
	private final String language;
}
