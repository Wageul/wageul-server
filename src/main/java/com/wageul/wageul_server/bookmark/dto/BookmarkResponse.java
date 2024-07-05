package com.wageul.wageul_server.bookmark.dto;

import java.time.LocalDateTime;

import com.wageul.wageul_server.bookmark.domain.Bookmark;
import com.wageul.wageul_server.experience.dto.ExperienceNoWriterResponse;
import com.wageul.wageul_server.user.dto.UserSimpleProflieDto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BookmarkResponse {

	private final Long id;
	private final UserSimpleProflieDto user;
	private final ExperienceNoWriterResponse experience;
	private final LocalDateTime createdAt;

	@Builder
	public BookmarkResponse(Bookmark bookmark) {
		id = bookmark.getId();
		user = UserSimpleProflieDto.builder()
			.id(bookmark.getUser().getId())
			.profileImg(bookmark.getUser().getProfileImg())
			.name(bookmark.getUser().getName())
			.build();
		experience = ExperienceNoWriterResponse.builder()
			.id(bookmark.getExperience().getId())
			.title(bookmark.getExperience().getTitle())
			.location(bookmark.getExperience().getLocation())
			.datetime(bookmark.getExperience().getDatetime())
			.content(bookmark.getExperience().getContent())
			.duration(bookmark.getExperience().getDuration())
			.cost(bookmark.getExperience().getCost())
			.contact(bookmark.getExperience().getContact())
			.limitMember(bookmark.getExperience().getLimitMember())
			.writerId(bookmark.getExperience().getWriter().getId())
			.language(bookmark.getExperience().getLanguage())
			.build();
		createdAt = bookmark.getCreatedAt();
	}
}
