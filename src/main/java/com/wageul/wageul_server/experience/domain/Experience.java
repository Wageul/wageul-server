package com.wageul.wageul_server.experience.domain;

import com.wageul.wageul_server.experience.dto.ExperienceCreate;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Experience from(ExperienceCreate experienceCreate) {
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
                .build();
    }
}
