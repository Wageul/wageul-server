package com.wageul.wageul_server.experience.domain;

import com.wageul.wageul_server.experience.dto.ExperienceCreate;
import com.wageul.wageul_server.experience.dto.ExperienceUpdate;
import com.wageul.wageul_server.user.domain.User;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
public class Experience {
    private long id;
    private final String title;
    private String location;
    private LocalDateTime datetime;
    private String content;
    private LocalTime duration;
    private int cost;
    private String contact;
    private int limitMember;
    private String language;
    private User writer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Experience from(User writer, ExperienceCreate experienceCreate) {
        return Experience.builder()
                .title(experienceCreate.getTitle())
                .location(experienceCreate.getLocation())
                .datetime(experienceCreate.getDatetime())
                .content(experienceCreate.getContent())
                .duration(experienceCreate.getDuration())
                .cost(experienceCreate.getCost())
                .contact(experienceCreate.getContact())
                .limitMember(experienceCreate.getLimitMember())
                .language(experienceCreate.getLanguage())
                .writer(writer)
                .build();
    }

    public Experience update(ExperienceUpdate experienceUpdate) {
        return Experience.builder()
            .title(experienceUpdate.getTitle())
            .location(experienceUpdate.getLocation())
            .datetime(experienceUpdate.getDatetime())
            .content(experienceUpdate.getContent())
            .duration(experienceUpdate.getDuration())
            .cost(experienceUpdate.getCost())
            .contact(experienceUpdate.getContact())
            .limitMember(experienceUpdate.getLimitMember())
            .language(experienceUpdate.getLanguage())
            .writer(writer)
            .build();
    }

    public Experience withProfileUrl(User writer) {
        return Experience.builder()
                .writer(writer)
                .build();
    }
}
