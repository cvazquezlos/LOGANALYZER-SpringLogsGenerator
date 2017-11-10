package com.jayway.application;

import com.jayway.config.SpringBootRunner;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("mysql")
@WebAppConfiguration
@IntegrationTest
@SpringApplicationConfiguration(classes = SpringBootRunner.class)
public class RestTemplateBankApplicationIT {

	@Autowired
	DataSource dataSource;

	RestTemplate restTemplate;

	@Before
	public void setUp() {
		restTemplate = new TestRestTemplate("user", "secret");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.execute("TRUNCATE TABLE account_t");
		jdbcTemplate.execute("INSERT INTO account_t (account_number, balance) VALUES (1, 100)");
		jdbcTemplate.execute("INSERT INTO account_t (account_number, balance) VALUES (2, 200)");
	}

	@Test
	public void shouldGetAccount() throws Exception {
		System.out.println("Starting shouldGetAccount() method testing...");
		ResponseEntity<Map> responseEntity = restTemplate.getForEntity("http://localhost:8080/accounts/{accountNumber}",
				Map.class, 1);

		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
		assertThat(responseEntity.getHeaders().getContentType(), is(MediaType.APPLICATION_JSON));

		Map<String, Integer> account = responseEntity.getBody();
		assertThat(account.get("accountNumber"), is(1));
		assertThat(account.get("balance"), is(100));
		System.out.println("shouldGetAccount() method testing finished.");
	}

	@Test
	public void shouldDeleteAccount() throws Exception {
		System.out.println("Starting shouldDeleteAccount() method testing...");
		ResponseEntity<Map> responseEntity = restTemplate.exchange("http://localhost:8080/accounts/{accountNumber}",
				HttpMethod.DELETE, null, Map.class, 1);

		assertThat(responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));
		assertThat(responseEntity.getBody(), nullValue());
		System.out.println("shouldDeleteAccount() method testing finished.");
	}

	@Test
	public void shouldDepositToAccount() throws Exception {
		System.out.println("Starting shouldDepositToAccount() method testing...");
		// create payload
		Map<String, Integer> body = Collections.singletonMap("amount", 50);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, Integer>> entity = new HttpEntity<>(body, headers);

		// deposit
		ResponseEntity<Map> responseEntity = restTemplate
				.postForEntity("http://localhost:8080/accounts/{accountNumber}/deposit", entity, Map.class, 1);

		assertThat(responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));
		assertThat(responseEntity.getBody(), nullValue());
		System.out.println("shouldDepositToAccount() method testing finished.");
	}

	@Test
	public void shouldNotDepositNegativeAmount() {
		System.out.println("Starting shouldNotDepositNegativeAmount() method testing...");
		// create payload
		Map<String, Integer> body = Collections.singletonMap("amount", -10);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, Integer>> entity = new HttpEntity<>(body, headers);

		// deposit
		ResponseEntity<Void> responseEntity = restTemplate
				.postForEntity("http://localhost:8080/accounts/{accountNumber}/deposit", entity, Void.class, 1);

		assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
		System.out.println("shouldNotDepositNegativeAmount() method testing finished.");
	}

	@Test
	public void shouldWithdrawFromAccount() {
		System.out.println("Starting shouldWithdrawFromAccount() method testing...");
		// create payload
		Map<String, Integer> body = Collections.singletonMap("amount", 10);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, Integer>> entity = new HttpEntity<>(body, headers);

		// deposit
		ResponseEntity<Map> responseEntity = restTemplate
				.postForEntity("http://localhost:8080/accounts/{accountNumber}/withdraw", entity, Map.class, 1);

		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
		Map<String, Integer> account = responseEntity.getBody();
		assertThat(account.get("accountNumber"), is(1));
		assertThat(account.get("balance"), is(90));
		System.out.println("shouldWithdrawFromAccount() method testing finished.");
	}

	@Test
	public void shouldNotOverdraw() {
		System.out.println("Starting shouldNotOverdraw() method testing...");
		// create payload
		Map<String, Integer> body = Collections.singletonMap("amount", 200);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, Integer>> entity = new HttpEntity<>(body, headers);

		// withdraw
		ResponseEntity<Map> responseEntity = restTemplate
				.postForEntity("http://localhost:8080/accounts/{accountNumber}/withdraw", entity, Map.class, 1);

		assertThat(responseEntity.getStatusCode(), is(HttpStatus.CONFLICT));
		System.out.println("shouldNotOverdraw() method testing finished.");
	}

	@Test
	public void shouldGetAccounts() {
		System.out.println("Starting shouldGetAccounts() method testing...");
		ResponseEntity<List> responseEntity = restTemplate.getForEntity("http://localhost:8080/accounts", List.class);

		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
		List<Integer> accountNumbers = responseEntity.getBody();
		assertThat(accountNumbers.size(), is(2));
		assertThat(accountNumbers, contains(1, 2));
		System.out.println("shouldGetAccounts() method testing finished.");
	}

	@Test
	public void shouldCreateAccount() {
		System.out.println("Starting shouldCreateAccount() method testing...");
		ResponseEntity<Map> responseEntity = restTemplate.postForEntity("http://localhost:8080/accounts", null,
				Map.class);

		assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
		HttpHeaders headers = responseEntity.getHeaders();
		URI location = headers.getLocation();
		assertThat(location.toString(), startsWith("http://localhost:8080/accounts/"));
		System.out.println("shouldCreateAccount() method testing finished.");
	}

	@Test
	public void shouldNotGetUnknownAccount() {
		System.out.println("Starting shouldNotGetUnknownAccount() method testing...");
		ResponseEntity<Void> responseEntity = restTemplate
				.getForEntity("http://localhost:8080/accounts/{accountNumber}", Void.class, 0);

		assertThat(responseEntity.getStatusCode(), is(HttpStatus.NOT_FOUND));
		System.out.println("shouldNotGetUnknownAccount() method testing finished.");
	}

	@Test
	public void shouldTransfer() {
		System.out.println("Starting shouldTransfer() method testing...");
		// create payload
		Map<String, Integer> body = new HashMap<String, Integer>() {
			{
				put("fromAccountNumber", 1);
				put("toAccountNumber", 2);
				put("amount", 50);
			}
		};
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, Integer>> entity = new HttpEntity<>(body, headers);

		// transfer
		ResponseEntity<Map> responseEntity = restTemplate.postForEntity("http://localhost:8080/transfer", entity,
				Map.class);
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));
		assertThat(responseEntity.getBody(), nullValue());

		// verify first account
		responseEntity = restTemplate.getForEntity("http://localhost:8080/accounts/{accountNumber}", Map.class, 1);
		Map<String, Integer> account = responseEntity.getBody();
		assertThat(account.get("accountNumber"), is(1));
		assertThat(account.get("balance"), is(50));

		// verify second account
		responseEntity = restTemplate.getForEntity("http://localhost:8080/accounts/{accountNumber}", Map.class, 2);
		account = responseEntity.getBody();
		assertThat(account.get("accountNumber"), is(2));
		assertThat(account.get("balance"), is(250));
		System.out.println("shouldTransfer() method testing finished.");
	}

	@Test
	public void shouldNotOverdrawDuringTransfer() {
		System.out.println("Starting shouldNotOverdrawDuringTransfer() method testing...");
		// create payload
		Map<String, Integer> body = new HashMap<String, Integer>() {
			{
				put("fromAccountNumber", 1);
				put("toAccountNumber", 2);
				put("amount", 300);
			}
		};
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, Integer>> entity = new HttpEntity<>(body, headers);

		// transfer
		ResponseEntity<Map> responseEntity = restTemplate.postForEntity("http://localhost:8080/transfer", entity,
				Map.class);
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.CONFLICT));
		assertThat(responseEntity.getBody(), nullValue());
		System.out.println("shouldNotOverdrawDuringTransfer() method testing finished.");
	}
}