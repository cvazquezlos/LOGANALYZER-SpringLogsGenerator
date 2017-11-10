package com.jayway.application;

import com.jayway.config.SpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("mysql")
@WebAppConfiguration
@IntegrationTest
@SpringApplicationConfiguration(classes = { SpringBootRunner.class })
public class RestTemplateSecureBankApplicationIT {

	@Test
	public void shouldReturnOkIfProvidingGoodCredentials() {
		System.out.println("Starting shouldReturnOkIfProvidingGoodCredentials() method testing...");
		RestTemplate restTemplate = new TestRestTemplate("user", "secret");

		ResponseEntity<Map> responseEntity = restTemplate.getForEntity("http://localhost:8080/accounts/{accountNumber}",
				Map.class, 1);

		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
		System.out.println("shouldReturnOkIfProvidingGoodCredentials() method testing finished.");
	}

	@Test
	public void shouldNotGetAccountIfNotProvidingCredentials() {
		System.out.println("Starting shouldNotGetAccountIfNotProvidingCredentials() method testing...");
		RestTemplate restTemplate = new TestRestTemplate();

		ResponseEntity<Void> responseEntity = restTemplate
				.getForEntity("http://localhost:8080/accounts/{accountNumber}", Void.class, 1);

		assertThat(responseEntity.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
		System.out.println("shouldNotGetAccountIfNotProvidingCredentials() method testing finished.");
	}

	@Test
	public void shouldNotGetAccountIfProvidingBadCredentials() {
		System.out.println("Starting shouldNotGetAccountIfProvidingBadCredentials() method testing...");
		RestTemplate restTemplate = new TestRestTemplate("unknown", "password");

		ResponseEntity<Void> responseEntity = restTemplate
				.getForEntity("http://localhost:8080/accounts/{accountNumber}", Void.class, 1);

		assertThat(responseEntity.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
		System.out.println("shouldNotGetAccountIfProvidingBadCredentials() method testing finished.");
	}
}