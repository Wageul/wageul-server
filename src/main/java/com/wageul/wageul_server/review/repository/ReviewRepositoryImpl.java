package com.wageul.wageul_server.review.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.wageul.wageul_server.review.domain.Review;
import com.wageul.wageul_server.review.service.port.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl extends ReviewCustomRepositoryImpl implements ReviewRepository {

	private final ReviewJpaRepository reviewJpaRepository;

	@Override
	public Review save(Review review) {
		return reviewJpaRepository.save(ReviewEntity.from(review)).toModel();
	}

	@Override
	public Optional<Review> findById(Long reviewId) {
		return reviewJpaRepository.findById(reviewId).map(ReviewEntity::toModel);
	}

	@Override
	public void delete(Review review) {
		reviewJpaRepository.delete(ReviewEntity.from(review));
	}

	@Override
	public List<Review> findByTargetId(long userId) {
		return reviewJpaRepository.findByTargetId(userId).stream().map(ReviewEntity::toModel).toList();
	}

	@Override
	public void deleteById(Long id) {
		reviewJpaRepository.deleteById(id);
	}
}
