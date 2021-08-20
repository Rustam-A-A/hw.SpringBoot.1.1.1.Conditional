package ru.netology.SpringBootConditional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootConditionalApplicationTests {

	public static final int DEVAPP_PORT = 8080;
	public static final int PRODAPP_PORT = 8081;
	public static final String DEVAPP_EXCPECTED = "Current profile is dev";
	public static final String PRODAPP_EXCPECTED = "Current profile is production";

	@Autowired
	TestRestTemplate restTemplate;

	@Container
	public static GenericContainer<?> devapp = new GenericContainer<>("devapp")
			.withExposedPorts(DEVAPP_PORT);
	@Container
	public static GenericContainer<?> prodapp = new GenericContainer<>("prodapp")
			.withExposedPorts(PRODAPP_PORT);

	@BeforeAll
	public static void setUp() {
		devapp.start();
		prodapp.start();
	}

	@Test
	void contextLoads() {
		ResponseEntity<String> fromDevApp = restTemplate.getForEntity("http://localhost:" + devapp.getMappedPort(DEVAPP_PORT) + "/profile", String.class);
		ResponseEntity<String> fromProdApp = restTemplate.getForEntity("http://localhost:" + prodapp.getMappedPort(PRODAPP_PORT) + "/profile", String.class);

		Assertions.assertEquals(PRODAPP_EXCPECTED, fromProdApp.getBody());
		Assertions.assertEquals(DEVAPP_EXCPECTED, fromDevApp.getBody());

		System.out.println(prodapp.getMappedPort(PRODAPP_PORT) + ":" + PRODAPP_PORT + " : " + fromProdApp.getBody());
		System.out.println(devapp.getMappedPort(DEVAPP_PORT) + ":" + DEVAPP_PORT + " : " + fromDevApp.getBody());
	}

}
