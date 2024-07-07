package com.wageul.wageul_server.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.wageul.wageul_server.oauth2.dto.CustomOAuth2User;
import com.wageul.wageul_server.user.dto.UserDto;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String requestUri = request.getRequestURI();

		if (requestUri.matches("^\\/login(?:\\/.*)?$")) {
			filterChain.doFilter(request, response);
			return;
		}
		if (requestUri.matches("^\\/oauth2(?:\\/.*)?$")) {
			filterChain.doFilter(request, response);
			return;
		}

		//cookie들을 불러온 뒤 token Key에 담긴 쿠키를 찾음
		String authorization = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				log.info("cookie name is ! {}", cookie.getName());
				if (cookie.getName().equals("token")) {
					log.info("cookie value is ! {}", cookie.getValue());
					authorization = cookie.getValue();
				}
			}
		}

		//Authorization 헤더 검증
		if (authorization == null) {
			log.info("token is null!");
			filterChain.doFilter(request, response);

			//조건이 해당되면 메소드 종료 (필수)
			return;
		}

		//토큰
		String token = authorization;

		//토큰 소멸 시간 검증
		if (jwtUtil.isExpired(token)) {
			log.info("token is expired!");
			filterChain.doFilter(request, response);

			//조건이 해당되면 메소드 종료 (필수)
			return;
		}

		//토큰에서 userId 획득
		long userId = jwtUtil.getUserId(token);

		//userDto를 생성하여 값 set
		UserDto userDto = UserDto.builder()
			.id(userId)
			.build();

		//UserDetails에 회원 정보 객체 담기
		CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDto);

		//스프링 시큐리티 인증 토큰 생성
		Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());

		//세션에 사용자 등록
		SecurityContextHolder.getContext().setAuthentication(authToken);

		filterChain.doFilter(request, response);
	}
}
