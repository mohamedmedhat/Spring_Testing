package com.mohamed.fulltestingdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableCaching
@EnableScheduling
public class FullTestingDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FullTestingDemoApplication.class, args);
	}

}
