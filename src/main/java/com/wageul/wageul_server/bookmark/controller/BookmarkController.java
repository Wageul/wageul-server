package com.wageul.wageul_server.bookmark.controller;

import java.awt.print.Book;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wageul.wageul_server.bookmark.domain.Bookmark;
import com.wageul.wageul_server.bookmark.dto.BookmarkResponse;
import com.wageul.wageul_server.bookmark.service.BookmarkService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bookmark")
@RequiredArgsConstructor
public class BookmarkController {

	private final BookmarkService bookmarkService;

	@PostMapping
	public ResponseEntity<BookmarkResponse> create(
		@RequestBody Map<String, Long> data) {
		Long experienceId = data.get("experienceId");
		Bookmark bookmark = bookmarkService.create(experienceId);
		return ResponseEntity.ok().body(new BookmarkResponse(bookmark));
	}

	@DeleteMapping("/{experienceId}")
	public void delete(@PathVariable("experienceId") Long experienceId) {
		bookmarkService.delete(experienceId);
	}

	@GetMapping
	public ResponseEntity<List<BookmarkResponse>> getMyBookmark() {
		List<Bookmark> bookmarks = bookmarkService.getMyBookmark();
		List<BookmarkResponse> bookmarkResponses = bookmarks.stream().map(BookmarkResponse::new).toList();
		return ResponseEntity.ok().body(bookmarkResponses);
	}
}
