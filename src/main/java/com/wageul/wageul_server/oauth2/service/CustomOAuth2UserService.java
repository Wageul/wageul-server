package com.wageul.wageul_server.oauth2.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.wageul.wageul_server.oauth2.dto.CustomOAuth2User;
import com.wageul.wageul_server.oauth2.dto.GoogleResponse;
import com.wageul.wageul_server.oauth2.dto.OAuth2Response;
import com.wageul.wageul_server.user.domain.User;
import com.wageul.wageul_server.user.dto.UserDto;
import com.wageul.wageul_server.user.dto.UserUpdate;
import com.wageul.wageul_server.user.service.port.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		OAuth2User oAuth2User = super.loadUser(userRequest);

		log.info("hello this is.. {}", oAuth2User);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		OAuth2Response oAuth2Response = null;
		if (registrationId.equals("google")) {
			log.info("google login processing..");
			oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
		} else {
			return null;
		}

		String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
		User existData = userRepository.findByUsername(username).orElse(null);

		if (existData == null) {
			// 새로 가입
			User user = User.builder()
				.email(oAuth2Response.getEmail())
				.username(username)
				.name(oAuth2Response.getName())
				.build();

			user = userRepository.save(user);

			UserDto userDto = UserDto.builder()
				.id(user.getId())
				.email(oAuth2Response.getEmail())
				.name(oAuth2Response.getName())
				.username(username)
				.build();

			return new CustomOAuth2User(userDto);
		} else {
			// 정보 업데이트
			UserUpdate userUpdate = UserUpdate.builder()
				.name(oAuth2Response.getName())
				.build();

			existData = existData.update(userUpdate);

			User user = userRepository.save(existData);

			UserDto userDto = UserDto.builder()
				.id(user.getId())
				.email(oAuth2Response.getEmail())
				.name(oAuth2Response.getName())
				.username(username)
				.build();

			return new CustomOAuth2User(userDto);
		}
	}
}
