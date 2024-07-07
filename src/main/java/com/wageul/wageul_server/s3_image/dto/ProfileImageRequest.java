package com.wageul.wageul_server.s3_image.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProfileImageRequest {

	private MultipartFile file;
	private Long userId;

	public ProfileImageRequest() {}

	public ProfileImageRequest(MultipartFile file, Long userId) {
		this.file = file;
		this.userId = userId;
	}
}
