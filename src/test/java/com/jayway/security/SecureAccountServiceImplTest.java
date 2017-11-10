package com.jayway.security;

import com.jayway.config.ApplicationConfig;
import com.jayway.config.SecurityConfig;
import com.jayway.domain.Account;
import com.jayway.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfig.class, SecurityConfig.class })
// @ContextConfiguration({"/application-context.xml", "/security-context.xml"})
@ActiveProfiles("h2")
@Transactional
public class SecureAccountServiceImplTest {

	@Autowired
	AccountService secureAccountService;

	@Before
	public void setUp() {
		SecurityContextHolder.clearContext();
	}

	@Test
	public void accountOwnerShouldGetAccount() {
		System.out.println("Starting accountOwnerShouldGetAccount() method testing...");
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ACCOUNT_OWNER");
		authenticateWithAuthorities(authorities);

		Account account = secureAccountService.get(1);

		assertThat(account, notNullValue());
		System.out.println("accountOwnerShouldGetAccount() method testing finished.");
	}

	@Test(expected = AccessDeniedException.class)
	public void unAuthorizedShouldNotGetAccount() {
		System.out.println("Starting unAuthorizedShouldNotGetAccount() method testing...");
		authenticateWithAuthorities(AuthorityUtils.NO_AUTHORITIES);

		secureAccountService.get(1);
		System.out.println("unAuthorizedShouldNotGetAccount() method testing finished.");
	}

	@Test
	public void accountOwnerShouldDeposit() {
		System.out.println("Starting accountOwnerShouldDeposit() method testing...");
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ACCOUNT_OWNER");
		authenticateWithAuthorities(authorities);

		secureAccountService.deposit(1, 100);
		System.out.println("accountOwnerShouldDeposit() method testing finished.");
	}

	@Test(expected = AccessDeniedException.class)
	public void unAuthorizedShouldNotDeposit() {
		System.out.println("Starting unAuthorizedShouldNotDeposit() method testing...");
		authenticateWithAuthorities(AuthorityUtils.NO_AUTHORITIES);

		secureAccountService.deposit(1, 100);
		System.out.println("unAuthorizedShouldNotDeposit() method testing finished.");
	}

	@Test
	public void accountOwnerShouldWithdraw() {
		System.out.println("Starting accountOwnerShouldWithdraw() method testing...");
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ACCOUNT_OWNER");
		authenticateWithAuthorities(authorities);

		Account account = secureAccountService.withdraw(1, 50);

		assertThat(account, notNullValue());
		System.out.println("accountOwnerShouldWithdraw() method testing finished.");
	}

	@Test(expected = AccessDeniedException.class)
	public void unAuthorizedShouldNotWithdraw() {
		System.out.println("Starting unAuthorizedShouldNotWithdraw() method testing...");
		authenticateWithAuthorities(AuthorityUtils.NO_AUTHORITIES);

		secureAccountService.withdraw(1, 50);
		System.out.println("unAuthorizedShouldNotWithdraw() method testing finished.");
	}

	@Test
	public void accountOwnerShouldTransfer() {
		System.out.println("Starting accountOwnerShouldTransfer() method testing...");
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ACCOUNT_OWNER");
		authenticateWithAuthorities(authorities);

		secureAccountService.transfer(1, 2, 10);
		System.out.println("accountOwnerShouldTransfer() method testing finished.");
	}

	@Test(expected = AccessDeniedException.class)
	public void unAuthorizedShouldNotTransfer() {
		System.out.println("Starting unAuthorizedShouldNotTransfer() method testing...");
		authenticateWithAuthorities(AuthorityUtils.NO_AUTHORITIES);

		secureAccountService.transfer(1, 2, 10);
		System.out.println("unAuthorizedShouldNotTransfer() method testing finished.");
	}

	@Test
	public void accountOwnerShouldGetAllAccountNumbers() {
		System.out.println("Starting accountOwnerShouldGetAllAccountNumbers() method testing...");
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ACCOUNT_OWNER");
		authenticateWithAuthorities(authorities);

		List<Integer> allAccounts = secureAccountService.getAllAccountNumbers();

		assertThat(allAccounts, notNullValue());
		System.out.println("accountOwnerShouldGetAllAccountNumbers() method testing finished.");
	}

	@Test(expected = AccessDeniedException.class)
	public void unAuthorizedShouldGetAllAccountNumbers() {
		System.out.println("Starting unAuthorizedShouldGetAllAccountNumbers() method testing...");
		authenticateWithAuthorities(AuthorityUtils.NO_AUTHORITIES);

		secureAccountService.getAllAccountNumbers();
		System.out.println("unAuthorizedShouldGetAllAccountNumbers() method testing finished.");
	}

	private void authenticateWithAuthorities(List<GrantedAuthority> authorities) {
		TestingAuthenticationToken authenticationToken = new TestingAuthenticationToken("name", "password",
				authorities);
		authenticationToken.setAuthenticated(true);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}
}