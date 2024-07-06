package com.wageul.wageul_server.experience.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class ExperienceCreate {

    private String title;
    private String location;
    private LocalDateTime datetime;
    private String content;
    private LocalTime duration;
    private int cost;
    private String contact;
    private int limitMember;
    private String language;

    public ExperienceCreate() {}

    public ExperienceCreate(
            String title,
            String location,
            LocalDateTime datetime,
            String content,
            LocalTime duration,
            int cost,
            String contact,
            int limitMember,
            String language) {
        this.title = title;
        this.location = location;
        this.datetime = datetime;
        this.content = content;
        this.duration = duration;
        this.cost = cost;
        this.contact = contact;
        this.limitMember = limitMember;
        this.language = language;
    }
}
