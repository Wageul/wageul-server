package com.wageul.wageul_server.experience.service;

import com.wageul.wageul_server.experience.domain.Experience;
import com.wageul.wageul_server.experience.dto.ExperienceCreate;
import com.wageul.wageul_server.experience.dto.ExperienceUpdate;
import com.wageul.wageul_server.experience.service.port.ExperienceRepository;
import com.wageul.wageul_server.oauth2.AuthorizationUtil;
import com.wageul.wageul_server.user.domain.User;
import com.wageul.wageul_server.user.service.port.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final UserRepository userRepository;
    private final AuthorizationUtil authorizationUtil;

    public List<Experience> getAll() {
        return experienceRepository.findAll();
    }

    public Experience create(ExperienceCreate experienceCreate) {
        User writer = userRepository.getById(authorizationUtil.getLoginUserId());
        Experience experience = Experience.from(writer, experienceCreate);
        return experienceRepository.save(experience);
    }

    public Experience getById(long id) {
        return experienceRepository.findById(id);
    }

	public Experience update(long id, ExperienceUpdate experienceUpdate) {
        Experience experience = getById(id);
        experience = experience.update(experienceUpdate);
        return experienceRepository.save(experience);
	}
}
