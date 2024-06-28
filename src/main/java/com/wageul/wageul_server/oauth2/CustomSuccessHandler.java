package com.wageul.wageul_server.oauth2;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.wageul.wageul_server.jwt.JwtUtil;
import com.wageul.wageul_server.oauth2.dto.CustomOAuth2User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private final JwtUtil jwtUtil;

	@Value("${spring.client.url}")
	private String clientUrl;

	@Value("${spring.jwt.expire-length}")
	private int expireInt;

	@Value("${spring.jwt.expire-length}")
	private long expireLong;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		log.info("onAuthenticationSuccess");
		//OAuth2User
		CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

		long userId = customUserDetails.getId();

		log.info("[onAuthenticationSuccess] userId {}", userId);

		String token = jwtUtil.createJwt(userId, expireLong);

		response.addCookie(createCookie("token", token));
		response.sendRedirect(clientUrl);
	}

	private Cookie createCookie(String key, String value) {

		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(expireInt);
		cookie.setSecure(true);
		cookie.setDomain("localhost.com");
		cookie.setAttribute("SameSite", "None");
		cookie.setPath("/");
		cookie.setHttpOnly(true);

		return cookie;
	}
}
