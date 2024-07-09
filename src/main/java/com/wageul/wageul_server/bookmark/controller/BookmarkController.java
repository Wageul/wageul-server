package com.wageul.wageul_server.bookmark.controller;

import java.awt.print.Book;
import java.util.List;
import java.util.Map;

import com.wageul.wageul_server.experience.domain.Experience;
import com.wageul.wageul_server.s3_image.domain.ExImage;
import com.wageul.wageul_server.s3_image.dto.ExImageDto;
import com.wageul.wageul_server.s3_image.service.ExImageService;
import com.wageul.wageul_server.s3_image.service.S3ReadService;
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
	private final ExImageService exImageService;
	private final S3ReadService s3ReadService;

	@PostMapping
	public ResponseEntity<BookmarkResponse> create(
		@RequestBody Map<String, Long> data) {
		Long experienceId = data.get("experienceId");
		Bookmark bookmark = bookmarkService.create(experienceId);
		Experience experience = bookmark.getExperience();
		List<ExImageDto> exImageDtos = getExperienceWithImageUrl(experience);
		experience = experience.withUrl(exImageDtos);

		return ResponseEntity.ok().body(new BookmarkResponse(bookmark, experience));
	}

	@DeleteMapping("/{experienceId}")
	public void delete(@PathVariable("experienceId") Long experienceId) {
		bookmarkService.delete(experienceId);
	}

	@GetMapping
	public ResponseEntity<List<BookmarkResponse>> getMyBookmark() {
		List<Bookmark> bookmarks = bookmarkService.getMyBookmark();
		List<BookmarkResponse> bookmarkResponses = bookmarks.stream().map(bookmark -> {
			Experience experience = bookmark.getExperience();
			List<ExImageDto> exImageDtos = getExperienceWithImageUrl(experience);
			experience = experience.withUrl(exImageDtos);
			return new BookmarkResponse(bookmark, experience);
		}).toList();
		return ResponseEntity.ok().body(bookmarkResponses);
	}

	private List<ExImageDto> getExperienceWithImageUrl(Experience experience) {
		long experienceId = experience.getId();
		List<ExImage> exImages = exImageService.getExImagesByExperience(experienceId);
		return exImages
				.stream().map(exImage -> {
					String imageUrl = s3ReadService.readFile(exImage.getImage());
					return ExImageDto.builder()
							.id(exImage.getId())
							.image(imageUrl)
							.build();
				}).toList();
	}
}
