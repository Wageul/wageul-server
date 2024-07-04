package com.wageul.wageul_server.s3_image.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wageul.wageul_server.experience.domain.Experience;
import com.wageul.wageul_server.experience.service.port.ExperienceRepository;
import com.wageul.wageul_server.s3_image.domain.ExImage;
import com.wageul.wageul_server.s3_image.service.port.ExImageRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExImageService {

	private final ExperienceRepository experienceRepository;
	private final ExImageRepository exImageRepository;

	// DB 이미지 파일 저장
	public List<ExImage> saveExImage(List<String> savedfiles, long experienceId) {
		Experience experience = experienceRepository.findById(experienceId).orElse(null);
		return savedfiles.stream().map(file -> {
			ExImage exImage = ExImage.builder()
				.image(file)
				.experience(experience)
				.build();

			return exImageRepository.save(exImage);
		}).toList();
	}

	// DB 이미지 파일 삭제
	@Transactional
	public void deleteExImage(long experienceId) {
		Experience experience = experienceRepository.findById(experienceId).orElse(null);
		exImageRepository.deleteByExperience(experience);
	}

	// experience id로 이미지 조회
	public List<ExImage> getExImagesByExperience(long experienceId) {
		Experience experience = experienceRepository.findById(experienceId).orElse(null);
		return exImageRepository.findByExperience(experience);
	}
}
