package com.jayway.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.config.SpringBootRunner;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("mysql")
@WebAppConfiguration
@IntegrationTest
@SpringApplicationConfiguration(classes = SpringBootRunner.class)
public class RestAssuredBankApplicationIT {

	@Autowired
	DataSource dataSource;

	@Before
	public void setUp() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.execute("TRUNCATE TABLE account_t");
		jdbcTemplate.execute("INSERT INTO account_t (account_number, balance) VALUES (1, 100)");
		jdbcTemplate.execute("INSERT INTO account_t (account_number, balance) VALUES (2, 200)");
	}

	@Test
	public void shouldLog() {
		System.out.println("Starting shouldLog() method testing...");
		given().auth().basic("user", "secret").log().all().when().get("/accounts/1").then().log().all();
		System.out.println("shouldLog() method testing finished.");
	}

	@Test
	public void shouldGetSingleAccount() {
		System.out.println("Starting shouldGetSingleAccount() method testing...");
		given().auth().basic("user", "secret").when().get("/accounts/1").then().statusCode(HttpStatus.SC_OK)
				.contentType(MediaType.APPLICATION_JSON_VALUE).body("accountNumber", is(1)).body("balance", is(100));
		System.out.println("shouldGetSingleAccount() method testing finished.");
	}

	@Test
	public void shouldDeleteAccount() {
		System.out.println("Starting shouldDeleteAccount() method testing...");
		given().auth().basic("user", "secret").when().delete("/accounts/1").then().statusCode(HttpStatus.SC_NO_CONTENT);
		System.out.println("shouldDeleteAccount() method testing finished.");
	}

	@Test
	public void shouldDepositToAccount() {
		System.out.println("Starting shouldDepositToAccount() method testing...");
		Map<String, Integer> body = Collections.singletonMap("amount", 10);
		String json = toJsonString(body);

		given().auth().basic("user", "secret").request().contentType(MediaType.APPLICATION_JSON_VALUE).body(json).when()
				.post("/accounts/1/deposit").then().statusCode(HttpStatus.SC_NO_CONTENT);
		System.out.println("shouldDepositToAccount() method testing finished.");
	}

	@Test
	public void shouldNotDepositNegativeAmount() {
		System.out.println("Starting shouldNotDepositNegativeAmount() method testing...");
		Map<String, Integer> body = Collections.singletonMap("amount", -10);
		String json = toJsonString(body);

		given().auth().basic("user", "secret").request().contentType(MediaType.APPLICATION_JSON_VALUE).body(json).when()
				.post("/accounts/1/deposit").then().statusCode(HttpStatus.SC_BAD_REQUEST);
		System.out.println("shouldNotDepositNegativeAmount() method testing finished.");
	}

	@Test
	public void shouldWithdrawFromAccount() {
		System.out.println("Starting shouldWithdrawFromAccount() method testing...");
		Map<String, Integer> body = Collections.singletonMap("amount", 10);
		String json = toJsonString(body);

		given().auth().basic("user", "secret").request().contentType(MediaType.APPLICATION_JSON_VALUE).body(json).when()
				.post("/accounts/1/withdraw").then().statusCode(HttpStatus.SC_OK).body("accountNumber", is(1))
				.body("balance", is(90));
		System.out.println("shouldWithdrawFromAccount() method testing finished.");
	}

	@Test
	public void shouldNotOverdraw() {
		System.out.println("Starting shouldNotOverdraw() method testing...");
		Map<String, Integer> body = Collections.singletonMap("amount", 200);
		String json = toJsonString(body);

		given().auth().basic("user", "secret").request().contentType(MediaType.APPLICATION_JSON_VALUE).body(json).when()
				.post("/accounts/1/withdraw").then().statusCode(HttpStatus.SC_CONFLICT);
		System.out.println("shouldNotOverdraw() method testing finished.");
	}

	@Test
	public void shouldGetAccounts() {
		System.out.println("Starting shouldGetAccounts() method testing...");
		given().auth().basic("user", "secret").when().get("/accounts").then().statusCode(HttpStatus.SC_OK)
				.contentType(MediaType.APPLICATION_JSON_VALUE).body("size()", is(2))
				.body("findAll {it}", hasItems(1, 2));
		System.out.println("shouldGetAccounts() method testing finished.");
	}

	@Test
	public void shouldCreateAccount() {
		System.out.println("Starting shouldCreateAccount() method testing...");
		given().auth().basic("user", "secret").when().post("/accounts").then().statusCode(HttpStatus.SC_CREATED)
				.header(HttpHeaders.LOCATION, startsWith(baseURI))
				.header(HttpHeaders.LOCATION, containsString("/accounts/"));
		System.out.println("shouldCreateAccount() method testing finished.");
	}

	@Test
	public void shouldNotGetUnknownAccount() {
		System.out.println("Starting shouldNotGetUnknownAccount() method testing...");
		given().auth().basic("user", "secret").when().get("/accounts/0").then().statusCode(HttpStatus.SC_NOT_FOUND);
		System.out.println("shouldNotGetUnknownAccount() method testing finished.");
	}

	@Test
	public void shouldTransfer() {
		System.out.println("Starting shouldTransfer() method testing...");
		Map<String, Integer> body = new HashMap<String, Integer>() {
			{
				put("fromAccountNumber", 1);
				put("toAccountNumber", 2);
				put("amount", 50);
			}
		};
		String json = toJsonString(body);

		// transfer
		given().auth().basic("user", "secret").request().contentType(MediaType.APPLICATION_JSON_VALUE).body(json).when()
				.post("/transfer").then().statusCode(HttpStatus.SC_NO_CONTENT);

		// verify first account
		given().auth().basic("user", "secret").when().get("/accounts/1").then().statusCode(HttpStatus.SC_OK)
				.contentType(MediaType.APPLICATION_JSON_VALUE).body("accountNumber", is(1)).body("balance", is(50));

		// verify second account
		given().auth().basic("user", "secret").when().get("/accounts/2").then().statusCode(HttpStatus.SC_OK)
				.contentType(MediaType.APPLICATION_JSON_VALUE).body("accountNumber", is(2)).body("balance", is(250));
		System.out.println("shouldTransfer() method testing finished.");
	}

	@Test
	public void shouldNotOverdrawDuringTransfer() {
		System.out.println("Starting shouldNotOverdrawDuringTransfer() method testing...");
		Map<String, Integer> body = new HashMap<String, Integer>() {
			{
				put("fromAccountNumber", 1);
				put("toAccountNumber", 2);
				put("amount", 300);
			}
		};
		String json = toJsonString(body);

		given().auth().basic("user", "secret").request().contentType(MediaType.APPLICATION_JSON_VALUE).body(json).when()
				.post("/transfer").then().statusCode(HttpStatus.SC_CONFLICT);
		System.out.println("shouldNotOverdrawDuringTransfer() method testing finished.");
	}

	private static String toJsonString(Map<String, ?> map) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}