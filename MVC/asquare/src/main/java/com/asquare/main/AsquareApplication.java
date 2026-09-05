package com.asquare.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class AsquareApplication {

	public static void main(String[] args) {
		log.info("Starting Asquare application");
		SpringApplication.run(AsquareApplication.class, args);
	}

}
