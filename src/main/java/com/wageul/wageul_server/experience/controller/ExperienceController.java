package com.wageul.wageul_server.experience.controller;

import com.wageul.wageul_server.experience.domain.Experience;
import com.wageul.wageul_server.experience.dto.ExperienceCreate;
import com.wageul.wageul_server.experience.dto.ExperienceUpdate;
import com.wageul.wageul_server.experience.service.ExperienceService;

import com.wageul.wageul_server.s3_image.service.S3ReadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/experience")
@RequiredArgsConstructor
public class ExperienceController {

    private final ExperienceService experienceService;
    private final S3ReadService s3ReadService;

    @GetMapping
    public List<Experience> getAll() {
        List<Experience> experiences = experienceService.getAll();
        experiences = getExperiencesWithProfileUrl(experiences);
        return experiences;
    }

    @PostMapping
    public ResponseEntity<Experience> create(
        @RequestBody ExperienceCreate experienceCreate) {
        Experience experience = experienceService.create(experienceCreate);
        experience = getExperienceWithProfileUrl(experience);
        return ResponseEntity.ok().body(experience);
    }

    @GetMapping("/{experienceId}")
    public Experience getById(@PathVariable("experienceId") long experienceId) {
        Experience experience = experienceService.getById(experienceId);
        if (experience == null) {
            return null;
        }
        experience = getExperienceWithProfileUrl(experience);
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
        experience = getExperienceWithProfileUrl(experience);
        return ResponseEntity.ok().body(experience);
    }

    @DeleteMapping("/{experienceId}")
    public void delete(@PathVariable("experienceId") long experienceId) {
        experienceService.delete(experienceId);
    }

    private List<Experience> getExperiencesWithProfileUrl(List<Experience> experiences) {
        experiences = experiences.stream().map(this::getExperienceWithProfileUrl).toList();
        return experiences;
    }

    private Experience getExperienceWithProfileUrl(Experience experience) {
        String profile = experience.getWriter().getProfileImg();
        if (profile == null) {
            return experience;
        }
        String profileUrl = s3ReadService.readFile(profile);
        return experience.withProfileUrl(experience.getWriter().withProfileUrl(profileUrl));
    }
}
