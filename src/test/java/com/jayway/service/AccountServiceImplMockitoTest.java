package com.jayway.service;

import com.jayway.repository.AccountEntity;
import com.jayway.repository.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplMockitoTest {

	@Mock
	AccountRepository accountRepositoryMock;

	@Mock
	AccountEntity accountEntityMock;

	@InjectMocks
	AccountServiceImpl accountServiceImpl;

	@Test
	public void shouldDepositToAccount() {
		System.out.println("Starting shouldDepositToAccount() method testing...");
		when(accountRepositoryMock.findOne(1)).thenReturn(accountEntityMock);

		accountServiceImpl.deposit(1, 100);

		verify(accountEntityMock).deposit(100);
		System.out.println("shouldDepositToAccount() method testing finished.");
	}

	@Test
	public void shouldWithdrawFromAccount() {
		System.out.println("Starting shouldWithdrawFromAccount() method testing...");
		AccountEntity returnedAccountEntity = mock(AccountEntity.class);
		when(accountRepositoryMock.findOne(2)).thenReturn(accountEntityMock);
		when(accountRepositoryMock.save(accountEntityMock)).thenReturn(returnedAccountEntity);

		accountServiceImpl.withdraw(2, 200);

		verify(accountEntityMock).withdraw(200);
		System.out.println("shouldWithdrawFromAccount() method testing finished.");
	}

	@Test
	public void shouldTransferBetweenAccount() {
		System.out.println("Starting shouldTransferBetweenAccount() method testing...");
		AccountEntity fromAccountMock = mock(AccountEntity.class);
		AccountEntity toAccountMock = mock(AccountEntity.class);

		when(accountRepositoryMock.findOne(1)).thenReturn(fromAccountMock);
		when(accountRepositoryMock.findOne(2)).thenReturn(toAccountMock);

		accountServiceImpl.transfer(1, 2, 500);

		verify(fromAccountMock).withdraw(500);
		verify(toAccountMock).deposit(500);
		System.out.println("shouldTransferBetweenAccount() method testing finished.");
	}
}