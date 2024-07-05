package com.wageul.wageul_server.review.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.wageul.wageul_server.review.domain.Review;
import com.wageul.wageul_server.user.domain.User;
import com.wageul.wageul_server.user.repository.UserEntity;

import jakarta.persistence.EntityManager;

public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Review> findByTarget(User target) {
		String findQuery = "SELECT r FROM ReviewEntity AS r WHERE r.target = :target";
		return entityManager
			.createQuery(findQuery, ReviewEntity.class)
			.setParameter("target", UserEntity.from(target))
			.getResultList()
			.stream().map(ReviewEntity::toModel).toList();
	}
}
