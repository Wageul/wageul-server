package com.wageul.wageul_server.participation.service.dto;

import java.util.List;
import java.util.Optional;

import com.wageul.wageul_server.participation.domain.Participation;

public interface ParticipationRepository {

	Optional<Participation> findById(long id);

	List<Participation> findAll();

	Participation save(Participation participation);

	void delete(Participation participation);
}
