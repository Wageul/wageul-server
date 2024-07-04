package com.wageul.wageul_server.s3_image.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.wageul.wageul_server.experience.domain.Experience;
import com.wageul.wageul_server.experience.repository.ExperienceEntity;
import com.wageul.wageul_server.s3_image.domain.ExImage;
import com.wageul.wageul_server.s3_image.service.port.ExImageCustomRepository;

import jakarta.persistence.EntityManager;

public class ExImageCustomRepositoryImpl implements ExImageCustomRepository {

	@Autowired
	private EntityManager entityManager;

	@Override
	public void deleteByExperience(Experience experience) {
		String deleteQuery = "DELETE FROM ExImageEntity AS e WHERE e.experience = :experience";

		entityManager.createQuery(deleteQuery)
			.setParameter("experience", ExperienceEntity.from(experience))
			.executeUpdate();
	}

	@Override
	public List<ExImage> findByExperience(Experience experience) {
		String findQuery = "SELECT e FROM ExImageEntity AS e WHERE e.experience = :experience";

		return entityManager
			.createQuery(findQuery, ExImageEntity.class)
			.setParameter("experience", ExperienceEntity.from(experience))
			.getResultList()
			.stream().map(ExImageEntity::toModel).toList();
	}
}
