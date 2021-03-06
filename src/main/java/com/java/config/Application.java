package com.java.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = "com.java")
@EnableSwagger2
public class Application {

	public static void main(final String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
	
}
