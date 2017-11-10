package com.jayway.service;

import com.jayway.config.ApplicationConfig;
import com.jayway.domain.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
// @ContextConfiguration("classpath:application-context.xml")
@ActiveProfiles("h2")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class AccountServiceImplTest {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    AccountService accountService;

    @Autowired
    DataSource dataSource;

    JdbcTemplate jdbcTemplate;


    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Test
    public void shouldGetAccount() {
		System.out.println("Starting shouldGetAccount() method testing...");
        Account account = accountService.get(1);

        assertThat(account.getAccountNumber(), is(1));
        assertThat(account.getBalance(), is(100));
		System.out.println("shouldGetAccount() method testing finished.");
    }


    @Test(expected = UnknownAccountException.class)
    public void shouldThrowExceptionForFindingUnknownAccountNumber() {
		System.out.println("Starting shouldThrowExceptionForFindingUnknownAccountNumber() method testing...");
        accountService.get(-1);
		System.out.println("shouldThrowExceptionForFindingUnknownAccountNumber() method testing finished.");
    }


    @Test
    public void shouldDeposit() {
		System.out.println("Starting shouldDeposit() method testing...");
        accountService.deposit(1, 100);

        entityManager.flush();

        int balance = getBalance(1);
        assertThat(balance, is(200));
		System.out.println("shouldDeposit() method testing finished.");
    }


    @Test
    public void shouldWithdraw() {
		System.out.println("Starting shouldWithdraw() method testing...");
        accountService.withdraw(1, 50);

        entityManager.flush();

        int balance = getBalance(1);
        assertThat(balance, is(50));
		System.out.println("shouldWithdraw() method testing finished.");
    }


    @Test
    public void shouldNotOverdraw() {
		System.out.println("Starting shouldNotOverdraw() method testing...");
        try {
            accountService.withdraw(1, 200);
            entityManager.flush();
            fail("Expected ConstraintViolationException");
        } catch (ConstraintViolationException e) {
            // Expected
        }

        int balance = getBalance(1);
        assertThat(balance, is(100));
		System.out.println("shouldNotOverdraw() method testing finished.");
    }


    @Test
    public void shouldTransfer() {
		System.out.println("Starting shouldTransfer() method testing...");
        accountService.transfer(1, 2, 10);
        entityManager.flush();

        int firstBalance = getBalance(1);
        assertThat(firstBalance, is(90));

        int secondBalance = getBalance(2);
        assertThat(secondBalance, is(210));
		System.out.println("shouldTransfer() method testing finished.");
    }


    @Test
    public void shouldNotTransferIfOverdraw() {
		System.out.println("Starting shouldNotTransferIfOverdraw() method testing...");
        try {
            accountService.transfer(1, 2, 200);
            entityManager.flush();
            fail("Expected ConstraintViolationException");
        } catch (ConstraintViolationException e) {
            // Expected
        }

        int firstBalance = getBalance(1);
        assertThat(firstBalance, is(100));

        int secondBalance = getBalance(2);
        assertThat(secondBalance, is(200));
		System.out.println("shouldNotTransferIfOverdraw() method testing finished.");
    }


    @Test
    public void shouldNotTransferFromUnknownAccount() {
		System.out.println("Starting shouldNotTransferFromUnknownAccount() method testing...");
        try {
            accountService.transfer(-1, 2, 50);
            entityManager.flush();
            fail("Expected UnknownAccountException");
        } catch (UnknownAccountException e) {
            // Expected
        }

        int secondBalance = getBalance(2);
        assertThat(secondBalance, is(200));
		System.out.println("shouldNotTransferFromUnknownAccount() method testing finished.");
    }


    @Test
    public void shouldNotTransferToUnknownAccount() {
		System.out.println("Starting shouldNotTransferToUnknownAccount() method testing...");
        try {
            accountService.transfer(1, -2, 50);
            entityManager.flush();
            fail("Expected UnknownAccountException");
        } catch (UnknownAccountException e) {
            // Expected
        }

        int balance = getBalance(1);
        assertThat(balance, is(100));
		System.out.println("shouldNotTransferToUnknownAccount() method testing finished.");
    }


    @Test
    public void shouldGetAllAccountNumbers() {
		System.out.println("Starting shouldGetAllAccountNumbers() method testing...");
        List<Integer> allAccounts = accountService.getAllAccountNumbers();

        assertThat(allAccounts, hasItems(1, 2));
		System.out.println("shouldGetAllAccountNumbers() method testing finished.");
    }


    @Test(expected = UnknownAccountException.class)
    public void shouldThrowExceptionWhenDeletingUnknownAccountNumber() {
		System.out.println("Starting shouldThrowExceptionWhenDeletingUnknownAccountNumber() method testing...");
        accountService.deleteAccount(-1);
		System.out.println("shouldThrowExceptionWhenDeletingUnknownAccountNumber() method testing finished.");
    }


    int getBalance(Integer accountNumber) {
        return jdbcTemplate.queryForObject("SELECT balance FROM account_t WHERE account_number = ?", Integer.class, accountNumber);
    }
}
