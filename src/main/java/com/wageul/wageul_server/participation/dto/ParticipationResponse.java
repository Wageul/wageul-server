package com.wageul.wageul_server.participation.dto;

import com.wageul.wageul_server.experience.domain.Experience;
import com.wageul.wageul_server.experience.dto.ExperienceNoWriterResponse;
import com.wageul.wageul_server.participation.domain.Participation;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ParticipationResponse {
	private final long id;
	private final long userId;
	private final ExperienceNoWriterResponse experience;

	public ParticipationResponse(Participation participation) {
		this.id = participation.getId();
		this.userId = participation.getUser().getId();
		Experience experience = participation.getExperience();
		this.experience = ExperienceNoWriterResponse.builder()
				.id(experience.getId())
				.title(experience.getTitle())
				.location(experience.getLocation())
				.datetime(experience.getDatetime())
				.content(experience.getContent())
				.duration(experience.getDuration())
				.cost(experience.getCost())
				.contact(experience.getContact())
				.limitMember(experience.getLimitMember())
				.writerId(experience.getWriter().getId())
				.language(experience.getLanguage())
				.build();
	}

	@Builder
	public ParticipationResponse(Participation participation, Experience experience) {
		this.id = participation.getId();
		this.userId = participation.getUser().getId();
		this.experience = ExperienceNoWriterResponse.builder()
				.id(experience.getId())
				.title(experience.getTitle())
				.location(experience.getLocation())
				.datetime(experience.getDatetime())
				.content(experience.getContent())
				.duration(experience.getDuration())
				.cost(experience.getCost())
				.contact(experience.getContact())
				.limitMember(experience.getLimitMember())
				.writerId(experience.getWriter().getId())
				.language(experience.getLanguage())
				.exImageList(experience.getExImageList())
				.build();
	}
}
