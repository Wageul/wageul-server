package com.wageul.wageul_server.participation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wageul.wageul_server.experience.domain.Experience;
import com.wageul.wageul_server.experience.service.ExperienceService;
import com.wageul.wageul_server.participation.domain.Participation;
import com.wageul.wageul_server.participation.dto.ExperienceParticipationResponse;
import com.wageul.wageul_server.participation.dto.ParticipationCreate;
import com.wageul.wageul_server.participation.dto.ParticipationResponse;
import com.wageul.wageul_server.participation.service.ParticipationService;
import com.wageul.wageul_server.user.domain.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/participation")
@RequiredArgsConstructor
public class ParticipationController {

	private final ParticipationService participationService;
	private final ExperienceService experienceService;

	// 참여하기
	@PostMapping
	public ResponseEntity<ParticipationResponse> create(@RequestBody ParticipationCreate participationCreate) {
		Participation participation = participationService.create(participationCreate);
		ParticipationResponse participationResponse = new ParticipationResponse(participation);
		if (participation == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(participationResponse);
	}

	// 로그인한 사용자의 참여 체험 목록 조회
	@GetMapping
	public ResponseEntity<List<ParticipationResponse>> getAll() {
		List<Participation> participations = participationService.getAll();
		List<ParticipationResponse> participationResponses
			= participations.stream().map(ParticipationResponse::new).toList();

		return ResponseEntity.ok().body(participationResponses);
	}

	// 자신이 신청한 체험 참여 취소
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/{participationId}")
	public void delete(@PathVariable("participationId") long participationId) {
		participationService.delete(participationId);
	}

	// 체험 참여자 추방
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/decline/{participationId}/{userId}")
	public void decline(@PathVariable("participationId") long participationId) {
		participationService.decline(participationId);
	}

	// 체험 별 참여자 목록 조회
	@GetMapping("/experience/{experienceId}")
	public ResponseEntity<ExperienceParticipationResponse> getExperienceParticipations(@PathVariable("experienceId") long experienceId) {
		List<User> users = participationService.getExperienceParticipations(experienceId);
		ExperienceParticipationResponse epr
			= new ExperienceParticipationResponse(experienceId, users);
		return ResponseEntity.ok().body(epr);
	}

	// 전체 체험 별 참여자 목록 조회
	@GetMapping("/experience")
	public ResponseEntity<List<ExperienceParticipationResponse>> getAllExperienceParticipations() {
		List<Experience> experiences = experienceService.getAll();
		List<ExperienceParticipationResponse> eprs = experiences
			.stream()
			.map(
				experience -> {
					List<User> users = participationService.getExperienceParticipations(experience.getId());
					return new ExperienceParticipationResponse(experience.getId(), users);
				})
			.toList();

		return ResponseEntity.ok().body(eprs);
	}
}
