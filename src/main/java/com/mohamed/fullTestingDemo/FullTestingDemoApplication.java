package com.mohamed.fullTestingDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FullTestingDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FullTestingDemoApplication.class, args);
	}

}
