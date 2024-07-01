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

		log.info("[jwt] {}", token);

		response.addHeader("Set-Cookie", createCookie("token", token).toString());
		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.sendRedirect(clientUrl);
	}

	private ResponseCookie createCookie(String key, String value) {

		ResponseCookie cookie = ResponseCookie.from(key, value)
				.path("/")
				.sameSite("None")
				.httpOnly(true)
				.secure(true)
				// .domain("localhost")
				.maxAge(expireInt)
				.build();

		return cookie;
	}
}
