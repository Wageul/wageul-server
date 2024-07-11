package com.wageul.wageul_server.user.service;

import com.wageul.wageul_server.oauth2.AuthorizationUtil;
import com.wageul.wageul_server.user.domain.User;
import com.wageul.wageul_server.user.dto.UserUpdate;
import com.wageul.wageul_server.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthorizationUtil authorizationUtil;

    public User getById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getMyInfo(long loginId) {
        long userId = authorizationUtil.getLoginUserId();
        if(userId == loginId) {
            return userRepository.findById(loginId).orElse(null);
        } else {
            return null;
        }
    }

    @Transactional
    public User update(User user, UserUpdate userUpdate) {
        User updatedUser = user.update(userUpdate);
        updatedUser = userRepository.save(updatedUser);
        return updatedUser;
    }

    public void deleteById(long userId) {
        long loginUserId = authorizationUtil.getLoginUserId();
        if (loginUserId == userId) {
            User user = userRepository.findById(userId).orElseThrow(
                    () -> new RuntimeException("NO USER"));
            userRepository.deleteById(userId);
        } else {
            throw new RuntimeException("ONLY LOGIN USER CAN DELETE HIMSELF");
        }
    }
}
