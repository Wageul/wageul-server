package com.wageul.wageul_server.s3_image.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wageul.wageul_server.s3_image.domain.ExImage;
import com.wageul.wageul_server.s3_image.dto.ExImageRequest;
import com.wageul.wageul_server.s3_image.dto.ExImageResponse;
import com.wageul.wageul_server.s3_image.service.ExImageService;
import com.wageul.wageul_server.s3_image.service.S3DeleteService;
import com.wageul.wageul_server.s3_image.service.S3UploadService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class ExImageController {

	private final S3UploadService s3UploadService;
	private final S3DeleteService s3DeleteService;
	private final ExImageService exImageService;

	@PostMapping(path = "/ex-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ExImageResponse> uploadExImage(
		@ModelAttribute ExImageRequest exImageRequest) {

		log.info("deleting prev ExImage...");

		// S3에서 삭제
		List<ExImage> exImages = exImageService.getExImagesByExperience(exImageRequest.getExperienceId());
		List<String> fileNames = exImages.stream().map(ExImage::getImage).toList();
		s3DeleteService.deleteFiles(fileNames);

		// 이미 존재하는 이미지는 DB에서 모두 삭제
		exImageService.deleteExImage(exImageRequest.getExperienceId());

		log.info("uploading ExImage...");

		// 파일이 있는지
		if(exImageRequest.getFiles() == null) {
			throw new RuntimeException("NO FILE HERE");
		}

		List<String> savedfiles = s3UploadService.saveFiles(exImageRequest.getFiles(), "experience");

		exImageService.saveExImage(savedfiles, exImageRequest.getExperienceId());

		return ResponseEntity.ok().body(new ExImageResponse(savedfiles));
	}
}
