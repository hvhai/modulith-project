package com.codehunter.modulithproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ModulithProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModulithProjectApplication.class, args);
	}

}
