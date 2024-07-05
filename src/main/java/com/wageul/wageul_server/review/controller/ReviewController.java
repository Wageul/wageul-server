package com.wageul.wageul_server.review.controller;

import java.util.List;

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
import com.wageul.wageul_server.review.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;

	@PostMapping
	public ResponseEntity<Review> create(
		@RequestBody ReviewCreate reviewCreate) {
		Review review = reviewService.create(reviewCreate);

		return ResponseEntity.ok().body(review);
	}

	@DeleteMapping("/{reviewId}")
	public void delete(@PathVariable("reviewId") Long reviewId) {
		reviewService.delete(reviewId);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Review>> getUserReviews(@PathVariable("userId") Long userId) {
		List<Review> reviews = reviewService.getAllByTarget(userId);

		return ResponseEntity.ok().body(reviews);
	}
}
