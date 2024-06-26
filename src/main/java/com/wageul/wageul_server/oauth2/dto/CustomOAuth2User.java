package com.wageul.wageul_server.oauth2.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.wageul.wageul_server.user.dto.UserDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {

	private final UserDto userDto;

	@Override
	public Map<String, Object> getAttributes() {
		return null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();
		return collection;
	}

	@Override
	public String getName() {
		return userDto.getName();
	}

	public String getUsername() {
		return userDto.getUsername();
	}

	public String getEmail() {
		return userDto.getEmail();
	}

	public long getId() {
		return userDto.getId();
	}
}
