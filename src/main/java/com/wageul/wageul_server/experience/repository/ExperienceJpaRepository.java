package com.wageul.wageul_server.experience.repository;

import com.wageul.wageul_server.experience.domain.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperienceJpaRepository extends JpaRepository<ExperienceEntity, Long> {
    List<ExperienceEntity> findAllByWriterId(long userId);
}
