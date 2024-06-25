package com.wageul.wageul_server.experience.repository;

import com.wageul.wageul_server.experience.domain.Experience;
import jakarta.persistence.*;
import lombok.Getter;
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
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
