package com.laundry.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.laundry.main.auth.config.JwtProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class LaundryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaundryServiceApplication.class, args);
	}

}
