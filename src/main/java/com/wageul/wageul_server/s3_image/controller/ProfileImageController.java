package com.wageul.wageul_server.s3_image.controller;

import com.wageul.wageul_server.s3_image.dto.ProfileImageRequest;
import com.wageul.wageul_server.s3_image.dto.ProfileImageResponse;
import com.wageul.wageul_server.s3_image.service.ProfileImageService;
import com.wageul.wageul_server.s3_image.service.S3DeleteService;
import com.wageul.wageul_server.s3_image.service.S3ReadService;
import com.wageul.wageul_server.s3_image.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileImageController {

    private final S3UploadService s3UploadService;
    private final S3DeleteService s3DeleteService;
    private final S3ReadService s3ReadService;
    private final ProfileImageService profileImageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProfileImageResponse> uploadProfile(
            @ModelAttribute ProfileImageRequest profileImageRequest) {
        // S3에서 삭제
        String profileImage = profileImageService.getMyProfileImage();
        if (profileImage != null && !profileImage.equals("")) {
            s3DeleteService.deleteFile(profileImage);
        }

        // 파일이 있는지
        if(profileImageRequest.getFile() == null) {
            throw new RuntimeException("NO FILE HERE");
        }

        // S3에 새로 업로드
        String profileImageDir = s3UploadService.saveFile(profileImageRequest.getFile(), "profile");

        // file url과 사용자 id 응답
        ProfileImageResponse profileImageResponse = ProfileImageResponse.builder()
                .fileUrl(s3ReadService.readFile(profileImageDir))
                .userId(profileImageService.upload(profileImageDir).getId()) // DB에도 이미지 경로 저장
                .build();

        return ResponseEntity.ok().body(profileImageResponse);
    }
}
