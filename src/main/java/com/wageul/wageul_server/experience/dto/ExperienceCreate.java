package com.wageul.wageul_server.experience.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class ExperienceCreate {

    private final String title;
    private final String location;
    private final LocalDateTime datetime;
    private final String content;
    private final LocalTime duration;
    private final int cost;
    private final String contact;
    private final int limitMember;
    private final String language;
    private final long writerId;

    @Builder
    public ExperienceCreate(
            @JsonProperty("title") String title,
            @JsonProperty("location") String location,
            @JsonProperty("datetime") LocalDateTime datetime,
            @JsonProperty("content") String content,
            @JsonProperty("duration") LocalTime duration,
            @JsonProperty("cost") int cost,
            @JsonProperty("contact") String contact,
            @JsonProperty("limitMember") int limitMember,
            @JsonProperty("language") String language,
            @JsonProperty("userId") long writer) {
        this.title = title;
        this.location = location;
        this.datetime = datetime;
        this.content = content;
        this.duration = duration;
        this.cost = cost;
        this.contact = contact;
        this.limitMember = limitMember;
        this.language = language;
        this.writerId = writer;
    }
}
