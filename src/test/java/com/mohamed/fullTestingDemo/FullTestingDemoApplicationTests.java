package com.mohamed.fullTestingDemo;

import com.mohamed.fullTestingDemo.configuration.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class FullTestingDemoApplicationTests {

	@Test
	void contextLoads() {
	}

}
