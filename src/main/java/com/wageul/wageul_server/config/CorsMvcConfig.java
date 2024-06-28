package com.wageul.wageul_server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

	@Value("${spring.client.url}")
	private String clientUrl;

	@Override
	public void addCorsMappings(CorsRegistry corsRegistry) {
		corsRegistry.addMapping("/**")
			.allowedOrigins("http://localhost.com:3000", "http://m.hello.wageul.o-r.kr:3000")
			.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
			.allowCredentials(true)
			.allowedHeaders("*")
			.exposedHeaders("Set-Cookie")
			.allowedOrigins(clientUrl);
	}
}
