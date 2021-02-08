package com.app.handyman;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@SpringBootApplication
public class HandymanApplication {

	public static void main(String[] args) {
		SpringApplication.run(HandymanApplication.class, args);
	}

	@Autowired
	private Environment env;

	@Bean
	public WebFluxConfigurer corsConfigurer() {
		return new WebFluxConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				String urls = env.getProperty("cors.urls");
				registry.addMapping("/**").allowedMethods("GET", "POST").allowedOrigins(urls);
			}
		};
	}

}
