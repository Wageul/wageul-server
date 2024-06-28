package com.wageul.wageul_server.participation.repository;

import java.util.List;

import com.wageul.wageul_server.participation.domain.Participation;
import com.wageul.wageul_server.user.domain.User;

public interface ParticipationCustomRepository {
	List<Participation> findByUser(User user);
}
