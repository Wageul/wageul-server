package com.wageul.wageul_server.participation.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional
	public List<Participation> getAll() {
		long loginUserId = authorizationUtil.getLoginUserId();
		User user = userRepository.getById(loginUserId);
		return participationRepository.findByUser(user);
	}

	@Transactional
	public void delete(long id) {
		Participation participation = participationRepository.findById(id).orElse(null);
		long loginUserId = authorizationUtil.getLoginUserId();
		User user = userRepository.getById(loginUserId);
		// 체험을 신청한 당사자만 삭제 가능
		if (participation != null && participation.getUser().equals(user))
			participationRepository.delete(participation);
	}

	@Transactional
	public void decline(long id) {
		Participation participation = participationRepository.findById(id).orElse(null);
		long loginUserId = authorizationUtil.getLoginUserId();
		User loginUser = userRepository.getById(loginUserId);
		if(participation != null && participation.getExperience().getWriter().equals(loginUser)) {
			// participation이 존재하고, 추방을 요청한 로그인 유저가 작성자라면 추방 성공
			participationRepository.delete(participation);
		} else {
			throw new RuntimeException("NOT WRITER");
		}
	}

	@Transactional
	public List<User> getExperienceParticipations(long experienceId) {
		Experience experience = experienceRepository.findById(experienceId).orElse(null);
		List<Participation> participations = null;
		if(experience != null) {
			participations = participationRepository.findByExperience(experience);
			List<User> users = participations.stream().map(Participation::getUser).toList();
			return users;
		} else {
			throw new RuntimeException("NO EXPERIENCE");
		}
	}
}
