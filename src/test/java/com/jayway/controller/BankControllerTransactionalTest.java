package com.jayway.controller;


import com.jayway.config.ApplicationConfig;
import com.jayway.config.WebConfig;
import com.jayway.service.ImmutableAccount;
import com.jayway.service.UnknownAccountException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, ApplicationConfig.class})
@ActiveProfiles({"h2"})
@TransactionConfiguration
@Transactional
@WebAppConfiguration
public class BankControllerTransactionalTest {


    @Autowired
    BankController bankController;


    @Test
    public void shouldGetAccount() {
        ImmutableAccount immutableAccount = bankController.get(1);

        assertThat(immutableAccount.getBalance(), is(100));
    }


    @Test
    public void shouldDepositToAccount() {
        bankController.deposit(1, new Amount(50));

        ImmutableAccount immutableAccount = bankController.get(1);

        assertThat(immutableAccount.getBalance(), is(150));
    }


    @Test(expected = UnknownAccountException.class)
    public void shouldDeleteAccount() {
        bankController.delete(1);

        bankController.get(1);
    }
}
