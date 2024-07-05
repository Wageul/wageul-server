package com.wageul.wageul_server.bookmark.repository;

import java.util.List;
import java.util.Optional;

import com.wageul.wageul_server.bookmark.domain.Bookmark;
import com.wageul.wageul_server.experience.domain.Experience;
import com.wageul.wageul_server.user.domain.User;

public interface BookmarkCustomRepository {

	Optional<Bookmark> findByUserAndExperience(User loginUser, Experience experience);

	List<Bookmark> getAllByUser(User user);
}
