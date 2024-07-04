package com.wageul.wageul_server.s3_image.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wageul.wageul_server.experience.repository.ExperienceEntity;

public interface ExImageJpaRepository extends JpaRepository<ExImageEntity, Long> {

	List<ExImageEntity> findByExperience(ExperienceEntity experienceEntity);
}
