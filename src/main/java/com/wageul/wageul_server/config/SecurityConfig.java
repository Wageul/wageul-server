package com.wageul.wageul_server.config;

import java.util.Collections;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.wageul.wageul_server.jwt.JwtFilter;
import com.wageul.wageul_server.jwt.JwtUtil;
import com.wageul.wageul_server.oauth2.CustomSuccessHandler;
import com.wageul.wageul_server.oauth2.service.CustomOAuth2UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomOAuth2UserService customOAuth2UserService;
	private final CustomSuccessHandler customSuccessHandler;
	private final JwtUtil jwtUtil;

	@Value("${spring.client.url}")
	private String clientUrl;

	@Value("${spring.client.test-url}")
	private String testUrl;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
			.cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

				@Override
				public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

					CorsConfiguration configuration = new CorsConfiguration();

					configuration.setAllowedOrigins(Collections.singletonList(clientUrl));
					configuration.setAllowedOrigins(Collections.singletonList(testUrl));
					configuration.setAllowedMethods(Collections.singletonList("*"));
					configuration.setAllowCredentials(true);
					configuration.setAllowedHeaders(Collections.singletonList("*"));
					configuration.setMaxAge(3600L);

					configuration.setExposedHeaders(Collections.singletonList("Set-Cookie"));
					configuration.setExposedHeaders(Collections.singletonList("token"));
					configuration.setExposedHeaders(Collections.singletonList("Cookie"));

					return configuration;
				}
			}));

		//csrf disable
		http
			.csrf((auth) -> auth.disable());

		//From 로그인 방식 disable
		http
			.formLogin((auth) -> auth.disable());

		//HTTP Basic 인증 방식 disable
		http
			.httpBasic((auth) -> auth.disable());

		//JWTFilter 추가
		http
			.addFilterAfter(new JwtFilter(jwtUtil), OAuth2LoginAuthenticationFilter.class);

		//oauth2
		http
			.oauth2Login((oauth2) -> oauth2
				.userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
					.userService(customOAuth2UserService))
				.successHandler(customSuccessHandler));

		//경로별 인가 작업
		http
			.authorizeHttpRequests((auth) -> auth
				.requestMatchers(
						HttpMethod.GET,
						"/",
						"/api/experience",
						"/api/experience/**",
						"/api/participation/experience/**",
						"/api/upload/read-ex-image/**",
						"/api/user/**",
						"/api/review/user/**"
				).permitAll()
				.anyRequest().authenticated());

		//세션 설정 : STATELESS
		http
			.sessionManagement((session) -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// /api로 시작하는 url인 경우 401 상태코드를 반환하도록 예외 처리
		http
			.exceptionHandling(exceptionHandling -> exceptionHandling
				.defaultAuthenticationEntryPointFor(
					new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
					new AntPathRequestMatcher("/api/**")
				));

		// 로그아웃
		http
			.logout(logout -> logout
				.logoutUrl("/api/logout")
				.logoutSuccessUrl("/")
				.addLogoutHandler((request, response, authentication) -> {
							HttpSession session = request.getSession();
							session.invalidate();
						})
				.logoutSuccessHandler((request, response, authentication) ->
						response.sendRedirect("/"))
				.deleteCookies("JSESSIONID", "token"));

		return http.build();
	}
}