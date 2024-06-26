package com.wageul.wageul_server.oauth2;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.wageul.wageul_server.oauth2.dto.CustomOAuth2User;

@Component
public class AuthorizationUtilImpl implements AuthorizationUtil {
	@Override
	public long getLoginUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomOAuth2User userDetails = (CustomOAuth2User) authentication.getPrincipal();

		return userDetails.getId();
	}
}
