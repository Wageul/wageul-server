package com.wageul.wageul_server.participation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ParticipationJpaRepository extends JpaRepository<ParticipationEntity, Long>, ParticipationCustomRepository {
	Long countByUserIdAndExperienceId(long userId, long experienceId);

    List<ParticipationEntity> findByUserId(long userId);
}
