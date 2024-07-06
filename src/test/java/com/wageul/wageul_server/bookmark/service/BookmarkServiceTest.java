package com.wageul.wageul_server.bookmark.service;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.wageul.wageul_server.bookmark.domain.Bookmark;
import com.wageul.wageul_server.experience.domain.Experience;
import com.wageul.wageul_server.mock.FakeAuthorizationUtil;
import com.wageul.wageul_server.mock.FakeBookmarkRepository;
import com.wageul.wageul_server.mock.FakeExperienceRepository;
import com.wageul.wageul_server.mock.FakeUserRepository;
import com.wageul.wageul_server.user.domain.User;

class BookmarkServiceTest {

	BookmarkService bookmarkService;
	FakeBookmarkRepository bookmarkRepository;
	FakeUserRepository userRepository;
	FakeExperienceRepository experienceRepository;
	FakeAuthorizationUtil authorizationUtil;

	Long userId = 1L;

	@BeforeEach
	void init() {
		bookmarkRepository = new FakeBookmarkRepository();
		userRepository = new FakeUserRepository();
		experienceRepository = new FakeExperienceRepository();
		authorizationUtil = new FakeAuthorizationUtil(userId);
		bookmarkService = new BookmarkService(
			bookmarkRepository,
			userRepository,
			experienceRepository,
			authorizationUtil
		);
	}

	@Test
	void 북마크추가() {
		// given
		User user = User.builder()
			.id(userId)
			.email("abc@gmail.com")
			.name("test")
			.build();

		userRepository.save(user);

		Experience experience = Experience.builder()
			.id(1L)
			.title("experience!")
			.content("new experience")
			.build();

		experienceRepository.save(experience);

		// when
		Bookmark bookmark = bookmarkService.create(experience.getId());

		// then
		Assertions.assertThat(bookmark.getUser()).isEqualTo(user);
		Assertions.assertThat(bookmark.getExperience()).isEqualTo(experience);
	}

	@Test
	void 북마크추가는_한번만가능() {
		// given
		User user = User.builder()
			.id(userId)
			.email("abc@gmail.com")
			.name("test")
			.build();

		userRepository.save(user);

		Experience experience = Experience.builder()
			.id(1L)
			.title("experience!")
			.content("new experience")
			.build();

		experienceRepository.save(experience);

		// when
		Bookmark bookmark = bookmarkService.create(experience.getId());

		// then
		Assertions.assertThatThrownBy(() ->
			bookmarkService.create(experience.getId())
		).hasMessage("ALREADY BOOKMARKED");
	}

	@Test
	void 북마크삭제() {
		// given
		User user = User.builder()
			.id(userId)
			.email("abc@gmail.com")
			.name("test")
			.build();

		userRepository.save(user);

		Experience experience = Experience.builder()
			.id(1L)
			.title("experience!")
			.content("new experience")
			.build();

		experienceRepository.save(experience);

		Bookmark bookmark = bookmarkService.create(experience.getId());

		// when
		bookmarkService.delete(experience.getId());

		// then
		Assertions.assertThat(bookmarkService.getMyBookmark().size()).isEqualTo(0);
	}

	@Test
	void 북마크읽기() {
		// given
		User user = User.builder()
			.id(userId)
			.email("abc@gmail.com")
			.name("test")
			.build();

		userRepository.save(user);

		Experience experience = Experience.builder()
			.id(1L)
			.title("experience!")
			.content("new experience")
			.build();

		experienceRepository.save(experience);

		Bookmark bookmark = bookmarkService.create(experience.getId());

		// when
		List<Bookmark> myBookmark = bookmarkService.getMyBookmark();

		// then
		Assertions.assertThat(myBookmark.size()).isEqualTo(1);
		Assertions.assertThat(myBookmark.getLast().getUser()).isEqualTo(user);
		Assertions.assertThat(myBookmark.getLast().getExperience()).isEqualTo(experience);
	}
}