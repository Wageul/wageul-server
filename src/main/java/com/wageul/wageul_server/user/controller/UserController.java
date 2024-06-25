package com.wageul.wageul_server.user.controller;

import com.wageul.wageul_server.user.domain.User;
import com.wageul.wageul_server.user.dto.UserUpdate;
import com.wageul.wageul_server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> getById(@PathVariable("userId") long userId, @CookieValue(value = "token", required = false) String token) {
        User user = userService.getById(userId, token);
        if(user == null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/");
            return new ResponseEntity<User>(headers, HttpStatus.FOUND);
        } else {
            return ResponseEntity.ok().body(user);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> update(
            @PathVariable("userId") long userId,
            @CookieValue("token") String token,
            @RequestBody UserUpdate userUpdate) {
        User user = userService.getById(userId, token);
        user = userService.update(user, userUpdate);
        return ResponseEntity.ok().body(user);
    }
}
