package com.wageul.wageul_server.bookmark.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.wageul.wageul_server.bookmark.domain.Bookmark;
import com.wageul.wageul_server.experience.domain.Experience;
import com.wageul.wageul_server.experience.repository.ExperienceEntity;
import com.wageul.wageul_server.review.repository.ReviewEntity;
import com.wageul.wageul_server.user.domain.User;
import com.wageul.wageul_server.user.repository.UserEntity;

import jakarta.persistence.EntityManager;

public class BookmarkCustomRepositoryImpl implements BookmarkCustomRepository {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Optional<Bookmark> findByUserAndExperience(User loginUser, Experience experience) {
		String findQuery = "SELECT b FROM BookmarkEntity AS b WHERE b.user = :user AND b.experience = :experience";
		return entityManager
			.createQuery(findQuery, BookmarkEntity.class)
			.setParameter("user", UserEntity.from(loginUser))
			.setParameter("experience", ExperienceEntity.from(experience))
			.getResultList()
			.stream().findAny().map(BookmarkEntity::toModel);
	}

	@Override
	public List<Bookmark> getAllByUser(User user) {
		String findQuery = "SELECT b FROM BookmarkEntity AS b WHERE b.user = :user";
		return entityManager
			.createQuery(findQuery, BookmarkEntity.class)
			.setParameter("user", UserEntity.from(user))
			.getResultList()
			.stream().map(BookmarkEntity::toModel).toList();
	}
}
