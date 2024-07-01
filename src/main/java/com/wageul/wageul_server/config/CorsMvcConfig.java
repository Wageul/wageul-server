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
			.allowCredentials(true)
			.allowedHeaders("*")
			.exposedHeaders("Set-Cookie")
			.allowedMethods("GET", "POST", "PUT", "DELETE")
			// .allowedOrigins("http://localhost:3000")
			.allowedOriginPatterns("*", "/**", "http://**", "localhost", "localhost:3000");
	}
}
