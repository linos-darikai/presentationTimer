package com.linos.presentationTimer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PresentationTimerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PresentationTimerApplication.class, args);
	}

}
