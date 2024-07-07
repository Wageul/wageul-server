package com.wageul.wageul_server.s3_image.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class ProfileImageResponse {

	private final String fileUrl;
	private final Long userId;
}
