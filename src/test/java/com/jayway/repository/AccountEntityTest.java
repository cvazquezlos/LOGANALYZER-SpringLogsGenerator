package com.jayway.repository;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AccountEntityTest {

	AccountEntity accountEntity;

	@Before
	public void setUp() {
		accountEntity = new AccountEntity();
	}

	@Test
	public void initialBalanceShouldBeZero() {
		System.out.println("Starting initialBalanceShouldBeZero() method testing...");
		assertThat(accountEntity.getBalance(), is(0));
		System.out.println("initialBalanceShouldBeZero() method testing finished.");
	}

	@Test
	public void shouldDeposit() {
		System.out.println("Starting shouldDeposit() method testing...");
		accountEntity.deposit(10);
		assertThat(accountEntity.getBalance(), is(10));
		System.out.println("shouldDeposit() method testing finished.");
	}

	@Test
	public void shouldWithdraw() {
		System.out.println("Starting shouldWithdraw() method testing...");
		accountEntity.withdraw(10);
		assertThat(accountEntity.getBalance(), is(-10));
		System.out.println("shouldWithdraw() method testing finished.");
	}
}