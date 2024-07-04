package com.wageul.wageul_server.s3_image.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3DeleteService {

	private final AmazonS3 amazonS3;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	// s3 파일 삭제
	public void deleteFiles(List<String> fileNames) {
		for (String fileName : fileNames) {
			log.info(fileName);
			amazonS3.deleteObject(bucket, fileName);
		}
	}
}
