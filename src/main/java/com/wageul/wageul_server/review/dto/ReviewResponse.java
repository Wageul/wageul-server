package com.wageul.wageul_server.review.dto;

import java.time.LocalDateTime;

import com.wageul.wageul_server.review.domain.Review;
import com.wageul.wageul_server.user.dto.UserSimpleProflieDto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewResponse {

	private final Long id;
	private final UserSimpleProflieDto writer;
	private final Long targetId;
	private final String content;
	private final Integer rate;
	private final LocalDateTime createdAt;
	private final LocalDateTime updatedAt;

	@Builder
	public ReviewResponse(Review review) {
		this.id = review.getId();
		this.writer = UserSimpleProflieDto.builder()
			.id(review.getWriter().getId())
			.profileImg(review.getWriter().getProfileImg())
			.name(review.getWriter().getName())
			.build();
		this.targetId = review.getTarget().getId();
		this.content = review.getContent();
		this.rate = review.getRate();
		this.createdAt = review.getCreatedAt();
		this.updatedAt = review.getUpdatedAt();
	}
}
