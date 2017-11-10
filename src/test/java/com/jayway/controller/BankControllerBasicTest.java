package com.jayway.controller;

import com.jayway.service.AccountService;
import com.jayway.service.ImmutableAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BankControllerBasicTest {

	@Mock
	AccountService accountServiceMock;

	@Mock
	ImmutableAccount immutableAccountMock;

	@InjectMocks
	BankController bankController;

	@Test
	public void shouldGetAccount() {
		System.out.println("Starting shouldGetAccount() method testing...");
		when(accountServiceMock.get(1)).thenReturn(immutableAccountMock);
		ImmutableAccount account = bankController.get(1);
		assertThat(account, is(immutableAccountMock));
		System.out.println("shouldGetAccount() method testing finished.");
	}

	@Test
	public void shouldDepositToAccount() {
		System.out.println("Starting shouldDepositToAccount() method testing...");
		bankController.deposit(1, new Amount(50));
		verify(accountServiceMock).deposit(1, 50);
		System.out.println("shouldDepositToAccount() method testing finished.");
	}

	@Test
	public void shouldDeleteAccount() {
		System.out.println("Starting shouldDeleteAccount() method testing...");
		bankController.delete(1);
		verify(accountServiceMock).deleteAccount(1);
		System.out.println("shouldDeleteAccount() method testing finished.");
	}
}