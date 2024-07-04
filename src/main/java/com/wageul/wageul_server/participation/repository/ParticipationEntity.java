package com.wageul.wageul_server.participation.repository;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.wageul.wageul_server.experience.repository.ExperienceEntity;
import com.wageul.wageul_server.participation.domain.Participation;
import com.wageul.wageul_server.user.repository.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "participations")
@EntityListeners(AuditingEntityListener.class)
public class ParticipationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private UserEntity user;

	@ManyToOne
	@JoinColumn(name = "experience_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private ExperienceEntity experience;

	@CreatedDate
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	public static ParticipationEntity from(Participation participation) {
		ParticipationEntity participationEntity = new ParticipationEntity();
		participationEntity.user = UserEntity.from(participation.getUser());
		participationEntity.experience = ExperienceEntity.from(participation.getExperience());
		participationEntity.createdAt = participation.getCreatedAt();
		return participationEntity;
	}

	public Participation toModel() {
		return Participation.builder()
			.id(id)
			.user(user.toModel())
			.experience(experience.toModel())
			.createdAt(createdAt)
			.build();
	}
}
