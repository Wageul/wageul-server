package com.wageul.wageul_server.s3_image.repository;

import com.wageul.wageul_server.s3_image.dto.ExImageDto;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.wageul.wageul_server.experience.repository.ExperienceEntity;
import com.wageul.wageul_server.s3_image.domain.ExImage;

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
@Table(name = "Ex_image")
@EntityListeners(AuditingEntityListener.class)
public class ExImageEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "image")
	private String image;

	@ManyToOne
	@JoinColumn(name = "experience_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private ExperienceEntity experience;

	public static ExImageEntity from(ExImage exImage) {
		ExImageEntity exImageEntity = new ExImageEntity();
		exImageEntity.image = exImage.getImage();
		exImageEntity.experience = ExperienceEntity.from(exImage.getExperience());

		return exImageEntity;
	}

	public ExImage toModel() {
		return ExImage.builder()
			.id(id)
			.image(image)
			.experience(experience.toModel())
			.build();
	}

	public ExImageDto toDto() {
		return ExImageDto.builder()
				.id(id)
				.image(image)
				.build();
	}
}
