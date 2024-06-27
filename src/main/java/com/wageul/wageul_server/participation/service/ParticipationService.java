package com.wageul.wageul_server.participation.service;

import org.springframework.stereotype.Service;

import com.wageul.wageul_server.experience.domain.Experience;
import com.wageul.wageul_server.experience.service.port.ExperienceRepository;
import com.wageul.wageul_server.oauth2.AuthorizationUtil;
import com.wageul.wageul_server.participation.domain.Participation;
import com.wageul.wageul_server.participation.dto.ParticipationCreate;
import com.wageul.wageul_server.participation.service.dto.ParticipationRepository;
import com.wageul.wageul_server.user.domain.User;
import com.wageul.wageul_server.user.service.port.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParticipationService {

	private final AuthorizationUtil authorizationUtil;
	private final ParticipationRepository participationRepository;
	private final ExperienceRepository experienceRepository;
	private final UserRepository userRepository;

	public Participation create(ParticipationCreate participationCreate) {
		User user = userRepository.findById(authorizationUtil.getLoginUserId()).orElse(null);
		Experience experience = experienceRepository.findById(participationCreate.getExperienceId()).orElse(null);
		if (user != null && experience != null) {
			Participation participation = Participation.from(user, experience);
			return participationRepository.save(participation);
		}
		return null;
	}
}
