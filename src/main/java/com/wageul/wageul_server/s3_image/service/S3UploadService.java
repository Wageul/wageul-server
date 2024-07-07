package com.wageul.wageul_server.s3_image.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3UploadService {

	private final AmazonS3 amazonS3;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	// s3 업로드
	public List<String> saveFiles(List<MultipartFile> multipartFile, String directory) {

		List<String> fileNameList = new ArrayList<>();

		multipartFile.forEach(file -> {
			String originalFilename = directory + "/" + createFileName(file.getOriginalFilename());
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(file.getSize());
			metadata.setContentType(file.getContentType());

			try {
				amazonS3.putObject(bucket, originalFilename, file.getInputStream(), metadata);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			fileNameList.add(originalFilename);
		});


		return fileNameList;
	}

	// s3 파일 하나 업로드
	public String saveFile(MultipartFile multipartFile, String directory) {
		String originalFilename = directory + "/" + createFileName(multipartFile.getOriginalFilename());
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(multipartFile.getSize());
		metadata.setContentType(multipartFile.getContentType());

		try {
			amazonS3.putObject(bucket, originalFilename, multipartFile.getInputStream(), metadata);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return originalFilename;
	}

	// 파일명 중복 방지 (UUID)
	private String createFileName(String fileName) {
		return UUID.randomUUID().toString().concat(getFileExtension(fileName));
	}

	// 파일 유효성 검사
	private String getFileExtension(String fileName) {
		if (fileName.isEmpty()) {
			throw new RuntimeException("INVALID FILE");
		}
		ArrayList<String> fileValidate = new ArrayList<>();
		fileValidate.add(".jpg");
		fileValidate.add(".jpeg");
		fileValidate.add(".png");
		fileValidate.add(".JPG");
		fileValidate.add(".JPEG");
		fileValidate.add(".PNG");
		String idxFileName = fileName.substring(fileName.lastIndexOf("."));
		if (!fileValidate.contains(idxFileName)) {
			throw new RuntimeException("INVALID FILE EXTENSION");
		}
		return fileName.substring(fileName.lastIndexOf("."));
	}
}
