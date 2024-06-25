package com.wageul.wageul_server.experience.service;

import com.wageul.wageul_server.experience.domain.Experience;
import com.wageul.wageul_server.mock.FakeExperienceRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExperienceServiceTest {

    ExperienceService experienceService;

    @BeforeEach
    void init() {
        experienceService = new ExperienceService(new FakeExperienceRepository());
    }

    @Test
    void 체험전체조회() {
        // given
        Experience experience = Experience.builder()
                .title("title")
                .location("location")
                .datetime(LocalDateTime.of(2024, 6, 25, 16, 41, 0))
                .content("content")
                .duration(LocalTime.of(4, 0, 0))
                .cost(10000)
                .contact("contact")
                .limitMember(15)
                .language("language")
                .build();

        // when
        for(int i = 0; i < 5; i++) {
            experienceService.create(experience);
        }
        List<Experience> experiences = experienceService.getAll();

        // then
        Assertions.assertThat(experiences.size()).isGreaterThan(0);
        Assertions.assertThat(experiences.size()).isEqualTo(5);
    }

    @Test
    void 체험생성() {
        // given
        Experience experience = Experience.builder()
                .title("title")
                .location("location")
                .datetime(LocalDateTime.of(2024, 6, 25, 16, 41, 0))
                .content("content")
                .duration(LocalTime.of(4, 0, 0))
                .cost(10000)
                .contact("contact")
                .limitMember(15)
                .language("language")
                .build();

        // when
        Experience newExperience = experienceService.create(experience);

        // then
        Assertions.assertThat(newExperience.getTitle()).isEqualTo("title");
        Assertions.assertThat(newExperience.getLocation()).isEqualTo("location");
        Assertions.assertThat(newExperience.getDatetime()).isEqualTo(LocalDateTime.of(2024, 6, 25, 16, 41, 0));
        Assertions.assertThat(newExperience.getContent()).isEqualTo("content");
        Assertions.assertThat(newExperience.getDuration()).isEqualTo(LocalTime.of(4, 0, 0));
        Assertions.assertThat(newExperience.getCost()).isEqualTo(10000);
        Assertions.assertThat(newExperience.getContact()).isEqualTo("contact");
        Assertions.assertThat(newExperience.getLimitMember()).isEqualTo(15);
        Assertions.assertThat(newExperience.getLanguage()).isEqualTo("language");
    }
}