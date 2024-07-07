package com.wageul.wageul_server.review.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewJpaRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findByTargetId(long userId);
}
