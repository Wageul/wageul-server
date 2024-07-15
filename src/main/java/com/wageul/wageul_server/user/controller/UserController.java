package com.wageul.wageul_server.user.controller;

import com.wageul.wageul_server.experience.service.ExperienceService;
import com.wageul.wageul_server.jwt.JwtUtil;
import com.wageul.wageul_server.oauth2.AuthorizationUtil;
import com.wageul.wageul_server.participation.service.ParticipationService;
import com.wageul.wageul_server.s3_image.service.S3ReadService;
import com.wageul.wageul_server.user.domain.User;
import com.wageul.wageul_server.user.dto.UserDetailDto;
import com.wageul.wageul_server.user.dto.UserUpdate;
import com.wageul.wageul_server.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    private final ExperienceService experienceService;
    private final ParticipationService participationService;
    private final S3ReadService s3ReadService;
    private final AuthorizationUtil authorizationUtil;

    @Value("${spring.client.url}")
    private String clientUrl;

    // userId에 해당하는 사용자 정보를, 해당 유저가 아니어도 가져올 수 있다.
    @GetMapping("/{userId}")
    public ResponseEntity<UserDetailDto> getById(
        @PathVariable("userId") long userId) {
        User user = userService.getById(userId);
        if(user == null) {
            log.info("USER NOT FOUND");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header(HttpHeaders.LOCATION, "/").build();
        }
        log.info("USER FOUND");
        // profileImg 경로를 S3 전체 경로로 변환해서 응답
        user = getUserResponse(user);
        long experienceCnt = experienceService.findByWriterId(user.getId()).size();
        long participationCnt = participationService.getUserParticipation(user.getId()).size();
        UserDetailDto userDetailDto = UserDetailDto.builder()
                .user(user)
                .createdExCnt(experienceCnt)
                .joinedPtCnt(participationCnt)
                .build();
        return ResponseEntity.ok().body(userDetailDto);
    }

    // 자신의 계정 정보를 가져온다.
    @GetMapping
    public ResponseEntity<UserDetailDto> getMyInfo() {
        User user = userService.getMyInfo(authorizationUtil.getLoginUserId());

        if(user == null) {
            log.info("USER NOT FOUND");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header(HttpHeaders.LOCATION, "/").build();
        }
        log.info("USER FOUND");

        // profileImg 경로를 S3 전체 경로로 변환해서 응답
        user = getUserResponse(user);
        long experienceCnt = experienceService.findByWriterId(user.getId()).size();
        long participationCnt = participationService.getUserParticipation(user.getId()).size();
        UserDetailDto userDetailDto = UserDetailDto.builder()
                .user(user)
                .createdExCnt(experienceCnt)
                .joinedPtCnt(participationCnt)
                .build();
        return ResponseEntity.ok().body(userDetailDto);
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
        response.setStatus(200);
    }

    private User getUserResponse(User user) {
        String profile = user.getProfileImg();
        if (profile == null || profile.equals("")) {
            return user;
        }

        return user.withProfileUrl(s3ReadService.readFile(profile));
    }
}
