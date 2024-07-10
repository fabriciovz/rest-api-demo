package com.fabribraguev.springboot.rest_api_demo;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(ApiAuthConfig.class)
public class RestApiDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiDemoApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(ApiAuthConfig apiAuthConfig){
		return args -> {
			System.out.println(apiAuthConfig);
		};
	}
}
