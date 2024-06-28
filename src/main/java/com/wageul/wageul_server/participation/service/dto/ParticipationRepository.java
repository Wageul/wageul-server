package com.wageul.wageul_server.participation.service.dto;

import java.util.List;
import java.util.Optional;

import com.wageul.wageul_server.participation.domain.Participation;
import com.wageul.wageul_server.participation.repository.ParticipationCustomRepository;

public interface ParticipationRepository extends ParticipationCustomRepository {

	Optional<Participation> findById(long id);

	List<Participation> findAll();

	Participation save(Participation participation);

	void delete(Participation participation);
}
