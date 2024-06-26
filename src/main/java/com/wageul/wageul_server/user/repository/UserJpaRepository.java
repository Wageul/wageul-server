package com.wageul.wageul_server.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.channels.FileChannel;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUsername(String username);
}
