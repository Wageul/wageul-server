package com.wageul.wageul_server.s3_image.dto;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExImageResponse {

	private final List<String> files;
}
