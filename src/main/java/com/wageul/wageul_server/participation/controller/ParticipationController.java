package com.wageul.wageul_server.participation.controller;

import java.util.List;

import com.wageul.wageul_server.participation.dto.ParticipationUserResponse;
import com.wageul.wageul_server.s3_image.domain.ExImage;
import com.wageul.wageul_server.s3_image.dto.ExImageDto;
import com.wageul.wageul_server.s3_image.service.ExImageService;
import com.wageul.wageul_server.s3_image.service.S3ReadService;
import com.wageul.wageul_server.user.dto.UserSimpleProfileDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	private final ExImageService exImageService;
	private final S3ReadService s3ReadService;

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
		List<ParticipationResponse> participationResponses = participations.stream().map(participation -> {
			Experience experience = participation.getExperience();
			List<ExImageDto> exImageDtos = getExperienceWithImageUrl(experience);
			experience = experience.withUrl(exImageDtos);
			return new ParticipationResponse(participation, experience);
		}).toList();

		return ResponseEntity.ok().body(participationResponses);
	}

	// 자신이 신청한 체험 참여 취소
	@DeleteMapping("/{participationId}")
	public void delete(@PathVariable("participationId") long participationId) {
		participationService.delete(participationId);
	}

	// 체험 참여자 추방
	@DeleteMapping("/decline/{participationId}/{userId}")
	public void decline(@PathVariable("participationId") long participationId) {
		participationService.decline(participationId);
	}

	// 체험 별 참여자 목록 조회
	@GetMapping("/experience/{experienceId}")
	public ResponseEntity<ExperienceParticipationResponse> getExperienceParticipations(@PathVariable("experienceId") long experienceId) {
		List<Participation> participations = participationService.getExperienceParticipations(experienceId);
		List<ParticipationUserResponse> purs = participations
				.stream()
				.map(participation -> {
					UserSimpleProfileDto usp = getUserWithProfileUrl(participation.getUser());
					return ParticipationUserResponse.builder()
							.participationId(participation.getId())
							.userProfile(usp)
							.build();
				})
				.toList();

		ExperienceParticipationResponse epr = ExperienceParticipationResponse.builder()
				.experienceId(experienceId)
				.userSimpleProfileList(purs)
				.build();
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
					List<Participation> participations = participationService.getExperienceParticipations(experience.getId());
					List<ParticipationUserResponse> purs = participations
							.stream()
							.map(participation -> {
								UserSimpleProfileDto usp = getUserWithProfileUrl(participation.getUser());
								return ParticipationUserResponse.builder()
										.participationId(participation.getId())
										.userProfile(usp)
										.build();
							})
							.toList();
					return ExperienceParticipationResponse.builder()
							.experienceId(experience.getId())
							.userSimpleProfileList(purs)
							.build();
				})
			.toList();

		return ResponseEntity.ok().body(eprs);
	}

	private UserSimpleProfileDto getUserWithProfileUrl(User user) {
		String profile = user.getProfileImg();
		if (profile == null) {
			return UserSimpleProfileDto.builder()
					.id(user.getId())
					.profileImg(user.getProfileImg())
					.name(user.getName())
					.build();
		}
		String profileUrl = s3ReadService.readFile(profile);
		user = user.withProfileUrl(profileUrl);

		return UserSimpleProfileDto.builder()
				.id(user.getId())
				.profileImg(user.getProfileImg())
				.name(user.getName())
				.build();
	}

	private List<ExImageDto> getExperienceWithImageUrl(Experience experience) {
		long experienceId = experience.getId();
		List<ExImage> exImages = exImageService.getExImagesByExperience(experienceId);
		return exImages
				.stream().map(exImage -> {
					String imageUrl = s3ReadService.readFile(exImage.getImage());
					return ExImageDto.builder()
							.id(exImage.getId())
							.image(imageUrl)
							.build();
				}).toList();
	}
}
