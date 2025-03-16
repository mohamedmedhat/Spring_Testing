package com.mohamed.fulltestingdemo;

import com.mohamed.fulltestingdemo.configuration.TestcontainersConfiguration;
import org.springframework.boot.SpringApplication;

public class TestFullTestingDemoApplication {

	public static void main(String[] args) {
		SpringApplication.from(FullTestingDemoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
