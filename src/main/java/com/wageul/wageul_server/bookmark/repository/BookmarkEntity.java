package com.wageul.wageul_server.bookmark.repository;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.wageul.wageul_server.bookmark.domain.Bookmark;
import com.wageul.wageul_server.experience.domain.Experience;
import com.wageul.wageul_server.experience.repository.ExperienceEntity;
import com.wageul.wageul_server.user.domain.User;
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
@Table(name = "bookmarks")
@EntityListeners(AuditingEntityListener.class)
public class BookmarkEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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

	public static BookmarkEntity from(Bookmark bookmark) {
		BookmarkEntity bookmarkEntity = new BookmarkEntity();
		bookmarkEntity.id = bookmark.getId();
		bookmarkEntity.user = UserEntity.from(bookmark.getUser());
		bookmarkEntity.experience = ExperienceEntity.from(bookmark.getExperience());

		return bookmarkEntity;
	}

	public Bookmark toModel() {
		return Bookmark.builder()
			.id(id)
			.user(user.toModel())
			.experience(experience.toModel())
			.createdAt(createdAt)
			.build();
	}
}
