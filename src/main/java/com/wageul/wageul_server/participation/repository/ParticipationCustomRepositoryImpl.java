package com.wageul.wageul_server.participation.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.wageul.wageul_server.participation.domain.Participation;
import com.wageul.wageul_server.user.domain.User;
import com.wageul.wageul_server.user.repository.UserEntity;

import jakarta.persistence.EntityManager;

public class ParticipationCustomRepositoryImpl implements ParticipationCustomRepository {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Participation> findByUser(User user) {
		String findQuery = "SELECT p FROM ParticipationEntity AS p WHERE p.user = :user";
		return entityManager
			.createQuery(findQuery, ParticipationEntity.class)
			.setParameter("user", UserEntity.from(user))
			.getResultList()
			.stream().map(ParticipationEntity::toModel).toList();
	}
}
