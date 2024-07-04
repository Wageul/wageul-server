package com.wageul.wageul_server.s3_image.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExImageRequest {

	private List<MultipartFile> files;
	private Long experienceId;

	public ExImageRequest() {}

	public ExImageRequest(List<MultipartFile> files, Long experienceId) {
		this.files = files;
		this.experienceId = experienceId;
	}
}
