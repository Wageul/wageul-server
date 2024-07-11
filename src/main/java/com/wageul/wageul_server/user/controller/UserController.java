package com.wageul.wageul_server.user.controller;

import com.wageul.wageul_server.jwt.JwtUtil;
import com.wageul.wageul_server.s3_image.service.S3ReadService;
import com.wageul.wageul_server.user.domain.User;
import com.wageul.wageul_server.user.dto.UserUpdate;
import com.wageul.wageul_server.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final S3ReadService s3ReadService;
    private final JwtUtil jwtUtil;

    // userId에 해당하는 사용자 정보를, 해당 유저가 아니어도 가져올 수 있다.
    @GetMapping("/{userId}")
    public ResponseEntity<User> getById(
        @PathVariable("userId") long userId) {
        User user = userService.getById(userId);
        if(user == null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/");
            return new ResponseEntity<User>(headers, HttpStatus.FOUND);
        } else {
            // profileImg 경로를 S3 전체 경로로 변환해서 응답
            user = getUserResponse(user);
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

            // profileImg 경로를 S3 전체 경로로 변환해서 응답
            user = getUserResponse(user);
            return ResponseEntity.ok().body(user);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> update(
            @PathVariable("userId") long userId,
            @RequestBody UserUpdate userUpdate) {
        User user = userService.getById(userId);
        user = userService.update(user, userUpdate);

        user = getUserResponse(user);

        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/{userId}")
    public void delete(
            @PathVariable("userId") long userId,
            HttpServletResponse response) throws IOException {
        userService.deleteById(userId);
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.sendRedirect("/");
    }

    private User getUserResponse(User user) {
        String profile = user.getProfileImg();
        if (profile == null || profile.equals("")) {
            return user;
        }

        return user.withProfileUrl(s3ReadService.readFile(profile));
    }
}
