package com.wageul.wageul_server.participation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationJpaRepository extends JpaRepository<ParticipationEntity, Long>, ParticipationCustomRepository {
	Long countByUserIdAndExperienceId(long userId, long experienceId);
}
