package com.wageul.wageul_server.bookmark.repository;

import org.springframework.stereotype.Repository;

import com.wageul.wageul_server.bookmark.domain.Bookmark;
import com.wageul.wageul_server.bookmark.service.port.BookmarkRepository;
import com.wageul.wageul_server.experience.domain.Experience;
import com.wageul.wageul_server.user.domain.User;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BookmarkRepositoryImpl extends BookmarkCustomRepositoryImpl implements BookmarkRepository {

	private final BookmarkJpaRepository bookmarkJpaRepository;

	@Override
	public Bookmark save(Bookmark bookmark) {
		return bookmarkJpaRepository.save(BookmarkEntity.from(bookmark)).toModel();
	}

	@Override
	public Long countByUserIdAndExperienceId(long userId, long experienceId) {
		return bookmarkJpaRepository.countByUserIdAndExperienceId(userId, experienceId);
	}

	@Override
	public void delete(Bookmark bookmark) {
		bookmarkJpaRepository.delete(BookmarkEntity.from(bookmark));
	}
}
