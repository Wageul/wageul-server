package com.wageul.wageul_server.review.service.port;

import java.util.List;
import java.util.Optional;

import com.wageul.wageul_server.review.domain.Review;
import com.wageul.wageul_server.user.domain.User;

public interface ReviewRepository {
	Review save(Review review);

	Optional<Review> findById(Long reviewId);

	void delete(Review review);

	List<Review> findByTarget(User target);
}
