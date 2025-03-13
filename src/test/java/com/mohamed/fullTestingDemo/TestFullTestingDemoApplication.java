package com.mohamed.fullTestingDemo;

import org.springframework.boot.SpringApplication;

public class TestFullTestingDemoApplication {

	public static void main(String[] args) {
		SpringApplication.from(FullTestingDemoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
