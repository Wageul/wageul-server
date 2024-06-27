package com.wageul.wageul_server.experience.service.port;

import com.wageul.wageul_server.experience.domain.Experience;

import java.util.List;
import java.util.Optional;

public interface ExperienceRepository {

    List<Experience> findAll();

    Experience save(Experience experience);

    Optional<Experience> findById(long id);

    public void deleteById(long id);
}
