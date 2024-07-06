package com.wageul.wageul_server.review.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewRateResponse {

	private final Double avg;
	private final List<ReviewResponse> reviews;

	@Builder
	public ReviewRateResponse(Double avg, List<ReviewResponse> reviewResponse) {
		this.avg = avg;
		this.reviews = reviewResponse;
	}
}
