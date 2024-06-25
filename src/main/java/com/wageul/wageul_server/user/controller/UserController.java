package com.wageul.wageul_server.user.controller;

import com.wageul.wageul_server.user.domain.User;
import com.wageul.wageul_server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@Controller
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @RequestMapping("/{userId}")
    public ResponseEntity<User> getById(@PathVariable("userId") long userId, @CookieValue(value = "token", required = false) String token) {
        User user = userService.findById(userId, token);
        if(user == null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/");
            return new ResponseEntity<User>(headers, HttpStatus.FOUND);
        } else {
            return ResponseEntity.ok().body(user);
        }
    }
}
