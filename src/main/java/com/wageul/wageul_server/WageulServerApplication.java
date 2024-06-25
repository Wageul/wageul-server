package com.wageul.wageul_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WageulServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WageulServerApplication.class, args);
	}

}
