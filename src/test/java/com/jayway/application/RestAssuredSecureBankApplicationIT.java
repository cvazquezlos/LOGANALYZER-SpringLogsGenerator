package com.jayway.application;

import com.jayway.config.SpringBootRunner;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("mysql")
@WebAppConfiguration
@IntegrationTest
@SpringApplicationConfiguration(classes = SpringBootRunner.class)
public class RestAssuredSecureBankApplicationIT {

	@Test
	public void shouldReturnOkIfProvidingGoodCredentials() {
		System.out.println("Starting shouldReturnOkIfProvidingGoodCredentials() method testing...");
		given().auth().basic("user", "secret").when().get("/accounts/1").then().statusCode(HttpStatus.SC_OK);
		System.out.println("shouldReturnOkIfProvidingGoodCredentials() method testing finished.");
	}

	@Test
	public void shouldNotGetAccountIfNotProvidingCredentials() {
		System.out.println("Starting shouldNotGetAccountIfNotProvidingCredentials() method testing...");
		when().get("/accounts/1").then().statusCode(HttpStatus.SC_UNAUTHORIZED);
		System.out.println("shouldNotGetAccountIfNotProvidingCredentials() method testing finished.");
	}

	@Test
	public void shouldNotGetAccountIfProvidingBadCredentials() {
		System.out.println("Starting shouldNotGetAccountIfProvidingBadCredentials() method testing...");
		given().auth().basic("unknown", "password").when().get("/accounts/1").then()
				.statusCode(HttpStatus.SC_UNAUTHORIZED);
		System.out.println("shouldNotGetAccountIfProvidingBadCredentials() method testing finished.");
	}
}