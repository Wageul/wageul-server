package com.wageul.wageul_server.experience.repository;

import com.wageul.wageul_server.experience.domain.Experience;
import com.wageul.wageul_server.user.repository.UserEntity;

import jakarta.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "experiences")
@EntityListeners(AuditingEntityListener.class)
public class ExperienceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "location")
    private String location;

    @Column(name = "datetime")
    private LocalDateTime datetime;

    @Column(name = "content")
    private String content;

    @Column(name = "duration")
    private LocalTime duration;

    @Column(name = "cost")
    private int cost;

    @Column(name = "contact")
    private String contact;

    @Column(name = "limit_member")
    private int limitMember;

    @Column(name = "language")
    private String language;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity writer;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static ExperienceEntity from(Experience experience) {
        ExperienceEntity experienceEntity = new ExperienceEntity();
        experienceEntity.id = experience.getId();
        experienceEntity.title = experience.getTitle();
        experienceEntity.location = experience.getLocation();
        experienceEntity.datetime = experience.getDatetime();
        experienceEntity.content = experience.getContent();
        experienceEntity.duration = experience.getDuration();
        experienceEntity.cost = experience.getCost();
        experienceEntity.contact = experience.getContact();
        experienceEntity.limitMember = experience.getLimitMember();
        experienceEntity.language = experience.getLanguage();
        experienceEntity.writer = UserEntity.from(experience.getWriter());
        experienceEntity.createdAt = experience.getCreatedAt();
        experienceEntity.updatedAt = experience.getUpdatedAt();
        return experienceEntity;
    }

    public Experience toModel() {
        return Experience.builder()
            .id(id)
            .title(title)
            .location(location)
            .datetime(datetime)
            .content(content)
            .duration(duration)
            .cost(cost)
            .contact(contact)
            .limitMember(limitMember)
            .language(language)
            .writer(writer.toModel())
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .build();
    }
}
