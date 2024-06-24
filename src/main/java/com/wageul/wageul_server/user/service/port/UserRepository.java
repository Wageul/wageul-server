package com.wageul.wageul_server.user.service.port;

import com.wageul.wageul_server.user.UserEntity;
import com.wageul.wageul_server.user.domain.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);
    User save(User user);
}
