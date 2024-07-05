package com.wageul.wageul_server.bookmark.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wageul.wageul_server.bookmark.domain.Bookmark;
import com.wageul.wageul_server.bookmark.service.port.BookmarkRepository;
import com.wageul.wageul_server.experience.domain.Experience;
import com.wageul.wageul_server.experience.service.port.ExperienceRepository;
import com.wageul.wageul_server.oauth2.AuthorizationUtil;
import com.wageul.wageul_server.user.domain.User;
import com.wageul.wageul_server.user.service.port.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookmarkService {

	private final BookmarkRepository bookmarkRepository;
	private final UserRepository userRepository;
	private final ExperienceRepository experienceRepository;
	private final AuthorizationUtil authorizationUtil;

	@Transactional
	public Bookmark create(Long experienceId) {
		User loginUser = userRepository.findById(authorizationUtil.getLoginUserId())
			.orElseThrow(() -> new RuntimeException("NO LOGIN USER INFO"));
		Experience experience = experienceRepository.findById(experienceId)
			.orElseThrow(() -> new RuntimeException("NO EXPERIENCE"));
		Bookmark bookmark = Bookmark.builder()
			.user(loginUser)
			.experience(experience)
			.build();
		return bookmarkRepository.save(bookmark);
	}

	@Transactional
	public void delete(Long experienceId) {
		User loginUser = userRepository.findById(authorizationUtil.getLoginUserId())
			.orElseThrow(() -> new RuntimeException("NO LOGIN USER INFO"));
		Experience experience = experienceRepository.findById(experienceId)
			.orElseThrow(() -> new RuntimeException("NO EXPERIENCE"));
		Bookmark bookmark = bookmarkRepository.findByUserAndExperience(loginUser, experience)
			.orElseThrow(() -> new RuntimeException("NO BOOKMARK"));
		bookmarkRepository.delete(bookmark);
	}

	@Transactional
	public List<Bookmark> getMyBookmark() {
		User loginUser = userRepository.findById(authorizationUtil.getLoginUserId())
			.orElseThrow(() -> new RuntimeException("NO LOGIN USER INFO"));
		return bookmarkRepository.getAllByUser(loginUser);
	}
}
