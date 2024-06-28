package com.wageul.wageul_server.participation.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.wageul.wageul_server.participation.domain.Participation;
import com.wageul.wageul_server.participation.service.dto.ParticipationRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ParticipationRepositoryImpl implements ParticipationRepository {

	private final ParticipationJpaRepository participationJpaRepository;

	@Override
	public Optional<Participation> findById(long id) {
		return participationJpaRepository.findById(id).map(ParticipationEntity::toModel);
	}

	@Override
	public List<Participation> findAll() {
		return participationJpaRepository.findAll().stream().map(ParticipationEntity::toModel).toList();
	}

	@Override
	public Participation save(Participation participation) {
		return participationJpaRepository.save(ParticipationEntity.from(participation)).toModel();
	}

	@Override
	public void delete(Participation participation) {
		participationJpaRepository.delete(ParticipationEntity.from(participation));
	}
}