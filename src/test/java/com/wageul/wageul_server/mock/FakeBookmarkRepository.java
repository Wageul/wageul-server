package com.wageul.wageul_server.mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.wageul.wageul_server.bookmark.domain.Bookmark;
import com.wageul.wageul_server.bookmark.service.port.BookmarkRepository;
import com.wageul.wageul_server.experience.domain.Experience;
import com.wageul.wageul_server.user.domain.User;

public class FakeBookmarkRepository implements BookmarkRepository {

	private Long autoGeneratedId = 0L;
	private final List<Bookmark> data = new ArrayList<>();

	@Override
	public Optional<Bookmark> findByUserAndExperience(User loginUser, Experience experience) {
		return data
			.stream()
			.filter(item -> item.getUser().equals(loginUser) && item.getExperience().equals(experience))
			.findAny();
	}

	@Override
	public List<Bookmark> getAllByUser(User user) {
		return data
			.stream()
			.filter(item -> item.getUser().equals(user))
			.toList();
	}

	@Override
	public Bookmark save(Bookmark bookmark) {
		if(bookmark.getId() == null || bookmark.getId() == 0) {
			Bookmark newBookmark = Bookmark.builder()
				.id(++autoGeneratedId)
				.user(bookmark.getUser())
				.experience(bookmark.getExperience())
				.createdAt(LocalDateTime.now())
				.build();
			data.add(newBookmark);
			return newBookmark;
		} else {
			data.removeIf(item -> Objects.equals(item.getId(), bookmark.getId()));
			data.add(bookmark);
			return bookmark;
		}
	}

	@Override
	public Long countByUserIdAndExperienceId(long userId, long experienceId) {
		return (long) data
			.stream()
			.map(item -> item.getUser().getId() == userId && item.getExperience().getId() == experienceId)
			.toList()
			.size();
	}

	@Override
	public void delete(Bookmark bookmark) {
		data.removeIf(item -> item.equals(bookmark));
	}
}
