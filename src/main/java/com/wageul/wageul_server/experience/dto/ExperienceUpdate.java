package com.wageul.wageul_server.experience.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ExperienceUpdate {

	private final String title;
	private final String location;
	private final LocalDateTime datetime;
	private final String content;
	private final LocalTime duration;
	private final int cost;
	private final String contact;
	private final int limitMember;
	private final String language;

	@Builder
	public ExperienceUpdate(
		@JsonProperty("title") String title,
		@JsonProperty("location") String location,
		@JsonProperty("datetime") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul") LocalDateTime datetime,
		@JsonProperty("content") String content,
		@JsonProperty("duration") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul") LocalTime duration,
		@JsonProperty("cost") int cost,
		@JsonProperty("contact") String contact,
		@JsonProperty("limitMember") int limitMember,
		@JsonProperty("language") String language) {
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
