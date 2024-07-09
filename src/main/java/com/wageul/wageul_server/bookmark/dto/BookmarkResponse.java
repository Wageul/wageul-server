package com.wageul.wageul_server.bookmark.dto;

import java.time.LocalDateTime;

import com.wageul.wageul_server.bookmark.domain.Bookmark;
import com.wageul.wageul_server.experience.domain.Experience;
import com.wageul.wageul_server.experience.dto.ExperienceNoWriterResponse;
import com.wageul.wageul_server.user.dto.UserSimpleProfileDto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BookmarkResponse {

	private final Long id;
	private final UserSimpleProfileDto user;
	private final ExperienceNoWriterResponse experience;
	private final LocalDateTime createdAt;

	@Builder
	public BookmarkResponse(Bookmark bookmark, Experience experience) {
		this.id = bookmark.getId();
		this.user = UserSimpleProfileDto.builder()
			.id(bookmark.getUser().getId())
			.profileImg(bookmark.getUser().getProfileImg())
			.name(bookmark.getUser().getName())
			.build();
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
		createdAt = bookmark.getCreatedAt();
	}
}
