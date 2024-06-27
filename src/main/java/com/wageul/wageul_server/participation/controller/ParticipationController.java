package com.wageul.wageul_server.participation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wageul.wageul_server.participation.domain.Participation;
import com.wageul.wageul_server.participation.dto.ParticipationCreate;
import com.wageul.wageul_server.participation.service.ParticipationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/participation")
@RequiredArgsConstructor
public class ParticipationController {

	private final ParticipationService participationService;

	@PostMapping("")
	public ResponseEntity<Participation> create(@RequestBody ParticipationCreate participationCreate) {
		Participation participation = participationService.create(participationCreate);
		if (participation == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(participation);
	}
}
