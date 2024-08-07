package com.wageul.wageul_server.review.repository;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.wageul.wageul_server.review.domain.Review;
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
@Table(name = "reviews")
@EntityListeners(AuditingEntityListener.class)
public class ReviewEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "writer_id")
	private UserEntity writer;

	@ManyToOne
	@JoinColumn(name = "target_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private UserEntity target;

	@Column(name = "content")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private String content;

	@Column(name = "rate")
	private Integer rate;

	@CreatedDate
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	public static ReviewEntity from(Review review) {
		ReviewEntity reviewEntity = new ReviewEntity();
		reviewEntity.id = review.getId();
		reviewEntity.writer = UserEntity.from(review.getWriter());
		reviewEntity.target = UserEntity.from(review.getTarget());
		reviewEntity.content = review.getContent();
		reviewEntity.rate = review.getRate();

		return reviewEntity;
	}

	public Review toModel() {
		return Review.builder()
			.id(id)
			.writer(writer.toModel())
			.target(target.toModel())
			.content(content)
			.rate(rate)
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.build();
	}
}
