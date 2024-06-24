package com.wageul.wageul_server.user.repository;

import com.wageul.wageul_server.user.UserEntity;
import com.wageul.wageul_server.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
}
