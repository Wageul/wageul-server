package com.wageul.wageul_server.participation.service;


import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.wageul.wageul_server.experience.domain.Experience;
import com.wageul.wageul_server.experience.service.port.ExperienceRepository;
import com.wageul.wageul_server.mock.FakeAuthorizationUtil;
import com.wageul.wageul_server.mock.FakeExperienceRepository;
import com.wageul.wageul_server.mock.FakeParticipationRepository;
import com.wageul.wageul_server.mock.FakeUserRepository;
import com.wageul.wageul_server.oauth2.AuthorizationUtil;
import com.wageul.wageul_server.participation.domain.Participation;
import com.wageul.wageul_server.participation.dto.ParticipationCreate;
import com.wageul.wageul_server.participation.service.dto.ParticipationRepository;
import com.wageul.wageul_server.user.domain.User;
import com.wageul.wageul_server.user.service.port.UserRepository;

class ParticipationServiceTest {

	private ParticipationService participationService;
	private AuthorizationUtil fakeAuthorizationUtil = new FakeAuthorizationUtil(1L);
	private ParticipationRepository fakeParticipationRepository = new FakeParticipationRepository();
	private ExperienceRepository fakeExperienceRepository = new FakeExperienceRepository();
	private UserRepository fakeUserRepository = new FakeUserRepository();

	@BeforeEach
	void init() {
		participationService = new ParticipationService(
			fakeAuthorizationUtil,
			fakeParticipationRepository,
			fakeExperienceRepository,
			fakeUserRepository);
	}

	@Test
	void 체험신청() {
		// given
		User user = User.builder()
			.id(1L)
			.email("abc@gmail.com")
			.name("test")
			.build();

		fakeUserRepository.save(user);

		Experience experience = Experience.builder()
			.id(1L)
			.title("experience!")
			.content("new experience")
			.build();

		fakeExperienceRepository.save(experience);

		ParticipationCreate participationCreate = ParticipationCreate.builder()
			.experienceId(experience.getId())
			.build();

		// when
		Participation participation = participationService.create(participationCreate);

		// then
		Assertions.assertThat(participation.getUser()).isEqualTo(user);
		Assertions.assertThat(participation.getExperience()).isEqualTo(experience);
	}

	@Test
	void 체험신청취소() {
		// given
		User user = User.builder()
			.id(1L)
			.email("abc@gmail.com")
			.name("test")
			.build();

		fakeUserRepository.save(user);

		Experience experience = Experience.builder()
			.id(1L)
			.title("experience!")
			.content("new experience")
			.build();

		fakeExperienceRepository.save(experience);

		ParticipationCreate participationCreate = ParticipationCreate.builder()
			.experienceId(experience.getId())
			.build();

		Participation participation = participationService.create(participationCreate);

		// when
		participationService.delete(participation.getId());

		// then
		Assertions.assertThat(participationService.getAll().size()).isEqualTo(0);
	}

	@Test
	void 체험별_참여자_조회() {
		// given
		User user = User.builder()
			.id(1L)
			.email("abc@gmail.com")
			.name("test")
			.build();

		fakeUserRepository.save(user);

		Experience experience = Experience.builder()
			.id(1L)
			.title("experience!")
			.content("new experience")
			.build();

		fakeExperienceRepository.save(experience);

		ParticipationCreate participationCreate = ParticipationCreate.builder()
			.experienceId(experience.getId())
			.build();

		Participation participation = participationService.create(participationCreate);

		// when
		List<User> experienceParticipations = participationService.getExperienceParticipations(experience.getId());

		// then
		Assertions.assertThat(experienceParticipations.size()).isEqualTo(1);
		Assertions.assertThat(experienceParticipations.getLast()).isEqualTo(user);
	}

}