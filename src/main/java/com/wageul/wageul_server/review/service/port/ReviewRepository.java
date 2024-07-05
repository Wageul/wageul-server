package com.wageul.wageul_server.review.service.port;

import java.util.Optional;

import com.wageul.wageul_server.review.domain.Review;
import com.wageul.wageul_server.review.repository.ReviewCustomRepository;

public interface ReviewRepository extends ReviewCustomRepository {
	Review save(Review review);

	Optional<Review> findById(Long reviewId);

	void delete(Review review);
}
