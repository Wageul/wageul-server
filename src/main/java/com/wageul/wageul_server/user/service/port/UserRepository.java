package com.wageul.wageul_server.user.service.port;

import com.wageul.wageul_server.user.domain.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);
    Optional<User> findById(long id);
    User save(User user);
}
