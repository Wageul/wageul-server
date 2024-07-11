package com.wageul.wageul_server.review.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wageul.wageul_server.oauth2.AuthorizationUtil;
import com.wageul.wageul_server.review.domain.Review;
import com.wageul.wageul_server.review.dto.ReviewCreate;
import com.wageul.wageul_server.review.dto.ReviewResponse;
import com.wageul.wageul_server.review.service.port.ReviewRepository;
import com.wageul.wageul_server.user.domain.User;
import com.wageul.wageul_server.user.service.port.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {

	private final UserRepository userRepository;
	private final ReviewRepository reviewRepository;
	private final AuthorizationUtil authorizationUtil;

	@Transactional
	public Review create(ReviewCreate reviewCreate) {
		User writer = userRepository.findById(authorizationUtil.getLoginUserId())
			.orElseThrow(() -> new RuntimeException("NO REVIEW WRITER INFO"));
		User target = userRepository.findById(reviewCreate.getTargetId())
			.orElseThrow(() -> new RuntimeException("NO REVIEW TARGET INFO"));

		Review review = Review.from(writer, target, reviewCreate);
		return reviewRepository.save(review);
	}

	@Transactional
	public void delete(Long reviewId) {
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new RuntimeException("NO DELETED REVIEW"));
		User loginUser = userRepository.findById(authorizationUtil.getLoginUserId())
				.orElseThrow(() -> new RuntimeException("NO LOGIN USER INFO"));
		if(review.getWriter().getId() != loginUser.getId()) {
			throw new RuntimeException("WRITER IS NOT EQUAL TO LOGIN USER");
		}
		reviewRepository.deleteById(review.getId());
	}

	public List<Review> findByTargetId(long userId) {
		return reviewRepository.findByTargetId(userId);
	}

	public Double getRate(List<Review> reviews) {
		List<ReviewResponse> reviewResponses = reviews.stream().map(ReviewResponse::new).toList();

		Double sum = 0D;
		for (Review review : reviews) {
			sum += review.getRate();
		}
		if (reviews.size() > 0) {
			return  sum / reviews.size();
		}
		return sum;
	}
}
