package com.wageul.wageul_server.review.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wageul.wageul_server.review.domain.Review;
import com.wageul.wageul_server.user.domain.User;

public interface ReviewJpaRepository extends JpaRepository<ReviewEntity, Long> {
	List<Review> findByTarget(User target);
}
