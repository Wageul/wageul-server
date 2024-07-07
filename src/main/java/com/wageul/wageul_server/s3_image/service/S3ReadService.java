package com.wageul.wageul_server.s3_image.service;

import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3ReadService {

	private final AmazonS3 amazonS3;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	public List<String> readFiles(List<String> fileNames) {
		return fileNames.stream().map(fileName -> amazonS3.getUrl(bucket, fileName).toString()).toList();
	}

	public String readFile(String fileName) {
		return amazonS3.getUrl(bucket, fileName).toString();
	}
}
