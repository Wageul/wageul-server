package com.wageul.wageul_server.experience.controller;

import com.wageul.wageul_server.experience.domain.Experience;
import com.wageul.wageul_server.experience.dto.ExperienceCreate;
import com.wageul.wageul_server.experience.service.ExperienceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RestController
@RequestMapping("/api/experience")
@RequiredArgsConstructor
public class ExperienceController {

    private final ExperienceService experienceService;

    @GetMapping("")
    public ResponseEntity<List<Experience>> getAll() {
        List<Experience> experiences = experienceService.getAll();
        return ResponseEntity.ok().body(experiences);
    }

    @PostMapping("")
    public ResponseEntity<Experience> create(
            @CookieValue("token") String token,
            @RequestBody ExperienceCreate experienceCreate) {
        if(token == null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/");
            return new ResponseEntity<Experience>(headers, HttpStatus.FOUND);
        }
        Experience experience = Experience.from(experienceCreate);
        experience = experienceService.create(experience);
        return ResponseEntity.ok().body(experience);
    }
}
