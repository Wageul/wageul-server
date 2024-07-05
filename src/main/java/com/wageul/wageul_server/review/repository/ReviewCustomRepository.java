package com.wageul.wageul_server.review.repository;

import java.util.List;

import com.wageul.wageul_server.review.domain.Review;
import com.wageul.wageul_server.user.domain.User;

public interface ReviewCustomRepository {

	List<Review> findByTarget(User target);
}
