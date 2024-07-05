package com.wageul.wageul_server.review.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReviewCreate {

	private final Long targetId;
	private final String content;
	private final Integer rate;
}
