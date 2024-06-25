package com.wageul.wageul_server.experience.service.port;

import com.wageul.wageul_server.experience.domain.Experience;

import java.util.List;

public interface ExperienceRepository {

    List<Experience> findAll();

    Experience save(Experience experience);
}
