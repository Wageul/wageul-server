package com.wageul.wageul_server.bookmark.service.port;

import com.wageul.wageul_server.bookmark.domain.Bookmark;
import com.wageul.wageul_server.bookmark.repository.BookmarkCustomRepository;
import com.wageul.wageul_server.experience.domain.Experience;
import com.wageul.wageul_server.user.domain.User;

public interface BookmarkRepository extends BookmarkCustomRepository {
	Bookmark save(Bookmark bookmark);

	void delete(Bookmark bookmark);
}
