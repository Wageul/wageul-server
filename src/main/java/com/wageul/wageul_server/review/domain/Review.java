package com.wageul.wageul_server.review.domain;

import java.time.LocalDateTime;

import com.wageul.wageul_server.review.dto.ReviewCreate;
import com.wageul.wageul_server.user.domain.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Review {

	private final Long id;
	private final User writer;
	private final User target;
	private final String content;
	private final Integer rate;
	private final LocalDateTime createdAt;
	private final LocalDateTime updatedAt;

	public static Review from(User writer, User target, ReviewCreate reviewCreate) {
		return Review.builder()
			.writer(writer)
			.target(target)
			.content(reviewCreate.getContent())
			.rate(reviewCreate.getRate())
			.build();
	}
}
