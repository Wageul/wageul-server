package com.wageul.wageul_server.review.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewCreate {

	private Long targetId;
	private String content;
	private Integer rate;

	public ReviewCreate() {}

	public ReviewCreate(
		Long targetId,
		String content,
		Integer rate) {
		this.targetId = targetId;
		this.content = content;
		this.rate = rate;
	}
}
