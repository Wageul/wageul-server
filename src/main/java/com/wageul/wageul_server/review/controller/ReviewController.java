package com.wageul.wageul_server.review.controller;

import com.wageul.wageul_server.s3_image.service.S3ReadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wageul.wageul_server.review.domain.Review;
import com.wageul.wageul_server.review.dto.ReviewCreate;
import com.wageul.wageul_server.review.dto.ReviewRateResponse;
import com.wageul.wageul_server.review.dto.ReviewResponse;
import com.wageul.wageul_server.review.service.ReviewService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;
	private final S3ReadService s3ReadService;

	@PostMapping
	public ResponseEntity<ReviewResponse> create(
		@RequestBody ReviewCreate reviewCreate) {
		Review review = reviewService.create(reviewCreate);

		String profile = review.getWriter().getProfileImg();
		if (profile == null) {
			return ResponseEntity.ok().body(new ReviewResponse(review));
		}
		String profileUrl = s3ReadService.readFile(profile);
		return ResponseEntity.ok().body(new ReviewResponse(review, profileUrl));
	}

	@DeleteMapping("/{reviewId}")
	public void delete(@PathVariable("reviewId") Long reviewId) {
		reviewService.delete(reviewId);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<ReviewRateResponse> getUserReviews(@PathVariable("userId") Long userId) {
		// 회원에 대한 후기 모두 가져오기
		List<Review> reviews = reviewService.findByTargetId(userId);

		// 후기의 별점 평균 계산하기
		Double reviewRate = reviewService.getRate(reviews);

		// review 회원 정보에 프로필 이미지 전체 경로 업데이트
		List<ReviewResponse> rres = reviews.stream().map(review -> {
			String profile = review.getWriter().getProfileImg();
			if (profile == null) {
				return new ReviewResponse(review);
			}
			String profileUrl = s3ReadService.readFile(profile);
			return new ReviewResponse(review, profileUrl);
		}).toList();

		// 후기 평균과 후기들을 묶어서 응답 제공
		ReviewRateResponse reviewRateResponse = ReviewRateResponse.builder()
				.avg(reviewRate)
				.reviewResponse(rres)
				.build();

		return ResponseEntity.ok().body(reviewRateResponse);
	}
}
