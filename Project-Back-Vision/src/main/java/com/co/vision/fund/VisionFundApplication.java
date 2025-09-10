package com.co.vision.fund;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VisionFundApplication {

	public static void main(String[] args) {
		SpringApplication.run(VisionFundApplication.class, args);
	}

}
