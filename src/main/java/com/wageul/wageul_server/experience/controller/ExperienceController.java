package com.wageul.wageul_server.experience.controller;

import com.wageul.wageul_server.experience.domain.Experience;
import com.wageul.wageul_server.experience.dto.ExperienceCreate;
import com.wageul.wageul_server.experience.dto.ExperienceUpdate;
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
    public List<Experience> getAll() {
        List<Experience> experiences = experienceService.getAll();
        return experiences;
    }

    @PostMapping("")
    public ResponseEntity<Experience> create(
        @CookieValue("token") String token,
        @RequestBody ExperienceCreate experienceCreate) {
        if (token == null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/");
            return new ResponseEntity<Experience>(headers, HttpStatus.FOUND);
        }
        Experience experience = experienceService.create(experienceCreate);
        return ResponseEntity.ok().body(experience);
    }

    @GetMapping("/{experienceId}")
    public Experience getById(@PathVariable("experienceId") long experienceId) {
        Experience experience = experienceService.getById(experienceId);
        if (experience == null) {
            return null;
        }
        return experience;
    }

    @PutMapping("/{experienceId}")
    public ResponseEntity<Experience> update(
        @PathVariable("experienceId") long experienceId,
        @CookieValue("token") String token,
        @RequestBody ExperienceUpdate experienceUpdate) {
        if (token == null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/");
            return new ResponseEntity<Experience>(headers, HttpStatus.FOUND);
        }
        Experience experience = experienceService.update(experienceId, experienceUpdate);
        return ResponseEntity.ok().body(experience);
    }

    @DeleteMapping("/{experienceId}")
    public void delete(@PathVariable("experienceId") long experienceId) {
        experienceService.delete(experienceId);
    }
}
