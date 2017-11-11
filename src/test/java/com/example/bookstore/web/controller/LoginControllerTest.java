package com.example.bookstore.web.controller;

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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.bookstore.domain.Account;
import com.example.bookstore.domain.support.AccountBuilder;
import com.example.bookstore.repository.JpaBookRepositoryTest;
import com.example.bookstore.service.AccountService;
import com.example.bookstore.service.AuthenticationException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class LoginControllerTest {

	private static final Logger logger = LogManager.getLogger(JpaBookRepositoryTest.class.getName());

    @Autowired
    private LoginController loginController;
    @Autowired
    private AccountService accountService;

    @Before
    public void setup() throws AuthenticationException {
        Account account = new AccountBuilder() {
            {
                address("Herve", "4650", "Rue de la station", "1", null, "Belgium");
                credentials("john", "secret");
                name("John", "Doe");
            }
        }.build(true);

        Mockito.when(this.accountService.login("john", "secret")).thenReturn(account);
    }

    @After
    public void verify() throws AuthenticationException {
        Mockito.verify(this.accountService, VerificationModeFactory.times(3)).login("john", "secret");
        Mockito.reset();
    }

    @Test
    public void testHandleLogin() throws AuthenticationException {
    	System.out.println("Starting testHandleLogin() method test...");

        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute(LoginController.REQUESTED_URL, "someUrl");

        String view = this.loginController.handleLogin("john", "secret", mockHttpSession);

        Account account = (Account) mockHttpSession.getAttribute(LoginController.ACCOUNT_ATTRIBUTE);

        assertNotNull(account);
        logger.info(account.toString().replaceAll("\n", " ").replaceAll("\r", " ") + " can't be null.");
        assertEquals("John", account.getFirstName());
        logger.info("John first name must be John");
        assertEquals("Doe", account.getLastName());
        logger.info("Doe first name must be Doe");
        assertNull(mockHttpSession.getAttribute(LoginController.REQUESTED_URL));
        logger.info("mockHttpSession is not null as expected.");
        assertEquals("redirect:someUrl", view);

        // Test the different view selection choices
        mockHttpSession = new MockHttpSession();
        view = this.loginController.handleLogin("john", "secret", mockHttpSession);
        assertEquals("redirect:/index.htm", view);

        mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute(LoginController.REQUESTED_URL, "abclogindef");
        view = this.loginController.handleLogin("john", "secret", mockHttpSession);
        assertEquals("redirect:/index.htm", view);
    	System.out.println("testHandleLogin() method test finished...");
    }

    @Configuration
    static class LoginControllerTestConfiguration {

        @Bean
        public AccountService accountService() {
            return Mockito.mock(AccountService.class);
        }

        @Bean
        public LoginController loginController() {
            return new LoginController();
        }
    }
}
