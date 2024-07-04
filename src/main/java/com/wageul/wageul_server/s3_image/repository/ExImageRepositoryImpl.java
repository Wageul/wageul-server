package com.wageul.wageul_server.s3_image.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wageul.wageul_server.experience.domain.Experience;
import com.wageul.wageul_server.experience.repository.ExperienceEntity;
import com.wageul.wageul_server.s3_image.domain.ExImage;
import com.wageul.wageul_server.s3_image.service.port.ExImageRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ExImageRepositoryImpl extends ExImageCustomRepositoryImpl implements ExImageRepository {

	private final ExImageJpaRepository exImageJpaRepository;

	@Override
	public ExImage save(ExImage exImage) {
		return exImageJpaRepository.save(ExImageEntity.from(exImage)).toModel();
	}

	@Override
	public List<ExImage> findByExperience(Experience experience) {
		return exImageJpaRepository.findByExperience(ExperienceEntity.from(experience))
			.stream().map(ExImageEntity::toModel).toList();
	}
}
