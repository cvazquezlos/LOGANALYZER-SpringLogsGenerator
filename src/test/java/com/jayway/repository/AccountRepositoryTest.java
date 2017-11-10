package com.jayway.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = EmbeddedDbJavaConfig.class)
// @ContextConfiguration("/embedded-db-application-context.xml")
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class AccountRepositoryTest {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	AccountRepository accountRepository;

	@Test
	public void shouldGetAccountByNumber() {
		System.out.println("Starting shouldGetAccountByNumber() method testing...");
		AccountEntity account = accountRepository.findOne(1);
		assertThat(account.getBalance(), is(100));
		System.out.println("shouldGetAccountByNumber() method testing finished.");
	}

	@Test
	public void newAccountsShouldHaveZeroBalance() {
		System.out.println("Starting newAccountsShouldHaveZeroBalance() method testing...");
		AccountEntity account = accountRepository.save(new AccountEntity());
		entityManager.flush();
		assertThat(account.getBalance(), is(0));
		System.out.println("newAccountsShouldHaveZeroBalance() method testing finished.");
	}

	@Test
	public void canDeleteAccount() {
		System.out.println("Starting canDeleteAccount() method testing...");
		accountRepository.delete(1);
		entityManager.flush();
		assertThat(accountRepository.findOne(1), nullValue());
		System.out.println("canDeleteAccount() method testing finished.");
	}
}