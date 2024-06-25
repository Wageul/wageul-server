package com.wageul.wageul_server.experience.repository;

import com.wageul.wageul_server.experience.domain.Experience;
import com.wageul.wageul_server.experience.service.port.ExperienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ExperienceRepositoryImpl implements ExperienceRepository {

    private final ExperienceJpaRepository experienceJpaRepository;

    @Override
    public List<Experience> findAll() {
        return experienceJpaRepository.findAll().stream().map(ExperienceEntity::toModel).toList();
    }

    @Override
    public Experience save(Experience experience) {
        return experienceJpaRepository.save(ExperienceEntity.from(experience)).toModel();
    }
}
