package com.example.bookstore.service;

import static org.junit.Assert.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.bookstore.domain.Account;
import com.example.bookstore.domain.support.AccountBuilder;
import com.example.bookstore.repository.AccountRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class AccountServiceTest {

	private static final Logger logger = LogManager.getLogger(AccountServiceTest.class.getName());

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;

    @Before
    public void setup() {
        Account account = new AccountBuilder() {
            {
                address("Herve", "4650", "Rue de la gare", "1", null, "Belgium");
                credentials("john", "secret");
                name("John", "Doe");
            }
        }.build(true);

        Mockito.when(accountRepository.findByUsername("john")).thenReturn(account);
    }

    @After
    public void verify() {
        Mockito.verify(accountRepository, VerificationModeFactory.times(1)).findByUsername(Mockito.anyString());
        // This is allowed here: using container injected mocks
        Mockito.reset(accountRepository);
    }

    @Test(expected = AuthenticationException.class)
    public void testLoginFailure() throws AuthenticationException {
    	System.out.println("Starting testLoginFailure() method test...");
        accountService.login("john", "fail");
        logger.info("Login failed as expected.");
    	System.out.println("testLoginFailure() method test finished...");
    }

    @Test()
    public void testLoginSuccess() throws AuthenticationException {
    	System.out.println("Starting testLoginSuccess() method test...");
        Account account = accountService.login("john", "secret");
        assertEquals("John", account.getFirstName());
        logger.info("John login success.");
        assertEquals("Doe", account.getLastName());
        logger.info("Doe login success.");
    	System.out.println("testLoginSuccess() method test finished...");
    }

    @Configuration
    static class AccountServiceTestContextConfiguration {

        @Bean
        public AccountService accountService() {
            return new AccountServiceImpl();
        }

        @Bean
        public AccountRepository accountRepository() {
            return Mockito.mock(AccountRepository.class);
        }
    }
}
