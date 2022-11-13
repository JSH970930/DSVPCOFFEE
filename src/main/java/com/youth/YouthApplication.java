package com.youth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class YouthApplication {

	public static void main(String[] args) {
		SpringApplication.run(YouthApplication.class, args);
	}

}
