package com.wageul.wageul_server.user.controller;

import com.wageul.wageul_server.jwt.JwtUtil;
import com.wageul.wageul_server.user.domain.User;
import com.wageul.wageul_server.user.dto.UserUpdate;
import com.wageul.wageul_server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/user", produces = "application/json")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    // userId에 해당하는 사용자 정보를, 해당 유저가 아니어도 가져올 수 있다.
    @GetMapping("/{userId}")
    public ResponseEntity<User> getById(
        @PathVariable("userId") long userId,
        @CookieValue("token") String token) {
        User user = userService.getById(userId);
        if(user == null || token == null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/");
            return new ResponseEntity<User>(headers, HttpStatus.FOUND);
        } else {
            return ResponseEntity.ok().body(user);
        }
    }

    // 자신의 계정 정보를 가져온다.
    @GetMapping
    public ResponseEntity<User> getMyInfo(@CookieValue("token") String token) {
        if(token == null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/");
            return new ResponseEntity<User>(headers, HttpStatus.FOUND);
        } else {
            long loginId = jwtUtil.getUserId(token);
            User user = userService.getMyInfo(loginId);
            return ResponseEntity.ok().body(user);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> update(
            @PathVariable("userId") long userId,
            @RequestBody UserUpdate userUpdate) {
        User user = userService.getById(userId);
        user = userService.update(user, userUpdate);
        return ResponseEntity.ok().body(user);
    }
}
