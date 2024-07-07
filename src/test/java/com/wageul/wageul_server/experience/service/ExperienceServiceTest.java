package com.wageul.wageul_server.experience.service;

import com.wageul.wageul_server.experience.domain.Experience;
import com.wageul.wageul_server.experience.dto.ExperienceCreate;
import com.wageul.wageul_server.experience.dto.ExperienceUpdate;
import com.wageul.wageul_server.experience.service.port.ExperienceRepository;
import com.wageul.wageul_server.mock.FakeAuthorizationUtil;
import com.wageul.wageul_server.mock.FakeExperienceRepository;
import com.wageul.wageul_server.mock.FakeUserRepository;
import com.wageul.wageul_server.user.domain.User;
import com.wageul.wageul_server.user.service.port.UserRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExperienceServiceTest {

    ExperienceService experienceService;
    ExperienceRepository experienceRepository = new FakeExperienceRepository();
    UserRepository userRepository = new FakeUserRepository();
    long userId = 1L;

    @BeforeEach
    void init() {
        experienceService = new ExperienceService(
            experienceRepository,
            userRepository,
            new FakeAuthorizationUtil(userId));
    }

    @Test
    void 체험전체조회() {
        // given
        User user = User.builder()
            .id(userId)
            .email("abc@gmail.com")
            .build();
        userRepository.save(user);
        ExperienceCreate experience = ExperienceCreate.builder()
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
        User user = User.builder()
            .id(userId)
            .email("abc@gmail.com")
            .build();
        userRepository.save(user);
        ExperienceCreate experience = ExperienceCreate.builder()
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
        Assertions.assertThat(newExperience.getWriter().getEmail()).isEqualTo("abc@gmail.com");
    }

    @Test
    void 체험하나조회() {
        // given
        User user = User.builder()
            .id(userId)
            .email("abc@gmail.com")
            .build();
        userRepository.save(user);
        ExperienceCreate experience = ExperienceCreate.builder()
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
        experienceService.create(experience);
        Experience experience1 = experienceService.getById(userId);

        // then
        Assertions.assertThat(experience1.getTitle()).isEqualTo("title");
        Assertions.assertThat(experience1.getLocation()).isEqualTo("location");
        Assertions.assertThat(experience1.getDatetime()).isEqualTo(LocalDateTime.of(2024, 6, 25, 16, 41, 0));
        Assertions.assertThat(experience1.getContent()).isEqualTo("content");
        Assertions.assertThat(experience1.getDuration()).isEqualTo(LocalTime.of(4, 0, 0));
        Assertions.assertThat(experience1.getCost()).isEqualTo(10000);
        Assertions.assertThat(experience1.getContact()).isEqualTo("contact");
        Assertions.assertThat(experience1.getLimitMember()).isEqualTo(15);
        Assertions.assertThat(experience1.getLanguage()).isEqualTo("language");
        Assertions.assertThat(experience1.getWriter().getEmail()).isEqualTo("abc@gmail.com");
    }

    @Test
    void 체험하나수정() {
        // given
        User user = User.builder()
            .id(userId)
            .email("abc@gmail.com")
            .build();
        userRepository.save(user);
        ExperienceCreate experience = ExperienceCreate.builder()
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
        Experience experience1 = experienceService.create(experience);
        ExperienceUpdate experienceUpdate = ExperienceUpdate.builder()
            .title("i am wonchae")
            .location("hello world")
            .datetime(LocalDateTime.of(2024, 6, 25, 16, 41, 0))
            .content("content")
            .duration(LocalTime.of(4, 0, 0))
            .cost(10000)
            .contact("contact")
            .limitMember(15)
            .language("language")
            .build();

        // when
        Experience updatedExperience = experienceService.update(experience1.getId(), experienceUpdate);

        // then
        Assertions.assertThat(updatedExperience.getTitle()).isEqualTo("i am wonchae");
        Assertions.assertThat(updatedExperience.getLocation()).isEqualTo("hello world");
        Assertions.assertThat(updatedExperience.getDatetime()).isEqualTo(LocalDateTime.of(2024, 6, 25, 16, 41, 0));
        Assertions.assertThat(updatedExperience.getContent()).isEqualTo("content");
        Assertions.assertThat(updatedExperience.getDuration()).isEqualTo(LocalTime.of(4, 0, 0));
        Assertions.assertThat(updatedExperience.getCost()).isEqualTo(10000);
        Assertions.assertThat(updatedExperience.getContact()).isEqualTo("contact");
        Assertions.assertThat(updatedExperience.getLimitMember()).isEqualTo(15);
        Assertions.assertThat(updatedExperience.getLanguage()).isEqualTo("language");
        Assertions.assertThat(updatedExperience.getWriter().getEmail()).isEqualTo("abc@gmail.com");
    }

    @Test
    void 체험삭제() {
        // given
        User user = User.builder()
            .id(userId)
            .email("abc@gmail.com")
            .build();
        userRepository.save(user);
        ExperienceCreate experience = ExperienceCreate.builder()
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
        Experience experience1 = experienceService.create(experience);

        // when
        experienceService.delete(experience1.getId());

        // then
        Assertions.assertThat(experienceService.getById(experience1.getId())).isNull();
    }

    @Test
    void 체험삭제는작성자만() {
        // given
        User user = User.builder()
            .id(userId)
            .email("abc@gmail.com")
            .build();
        userRepository.save(user);
        User user2 = User.builder()
            .id(2L)
            .email("abc@gmail.com")
            .build();
        userRepository.save(user2);
        ExperienceCreate experience = ExperienceCreate.builder()
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
        Experience experience1 = experienceService.create(experience);

        // when
        experienceService = new ExperienceService(
            experienceRepository,
            userRepository,
            new FakeAuthorizationUtil(2L));

        // then
        Assertions.assertThatThrownBy(
                () -> experienceService.delete(experience1.getId())
        ).hasMessage("ONLY WRITER CAN DELETE EXPERIENCE");
    }
}