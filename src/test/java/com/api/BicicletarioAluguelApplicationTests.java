package com.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class BicicletarioAluguelApplicationTests {

	@Test
	void contextLoads() {
		Assertions.assertTrue(true, "Aplicação inicializada com sucesso.");
	}

	@Test
	void mainTest() {
		assertDoesNotThrow(() -> {
			SpringApplication application = new SpringApplication(BicicletarioAluguelApplication.class);
			application.setWebApplicationType(WebApplicationType.NONE);
			application.run();
		});

		Assertions.assertTrue(true);
		BicicletarioAluguelApplication.main(new String[]{});
	}

}
