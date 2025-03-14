package com.mohamed.fullTestingDemo;

import com.mohamed.fullTestingDemo.configuration.TestcontainersConfiguration;
import org.springframework.boot.SpringApplication;

public class TestFullTestingDemoApplication {

	public static void main(String[] args) {
		SpringApplication.from(FullTestingDemoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
