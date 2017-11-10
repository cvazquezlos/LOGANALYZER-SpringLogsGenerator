package com.jayway.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MySqlRepositoryConfig.class)
@ActiveProfiles("mysql")
public class MySqlRepositoryConfigIT {

	@Autowired
	DataSource dataSource;

	@Autowired
	EntityManagerFactory entityManagerFactory;

	@Autowired
	PlatformTransactionManager transactionManager;

	@Test
	public void createsDataSource() {
		System.out.println("Starting createsDataSource() method testing...");
		assertNotNull(dataSource);
		System.out.println("createsDataSource() method testing finished.");
	}

	@Test
	public void createsEntityManagerFactory() {
		System.out.println("Starting createsEntityManagerFactory() method testing...");
		assertNotNull(entityManagerFactory);
		System.out.println("createsEntityManagerFactory() method testing finished.");
	}

	@Test
	public void createsTransactionManager() {
		System.out.println("Starting createsTransactionManager() method testing...");
		assertNotNull(transactionManager);
		System.out.println("createsTransactionManager() method testing finished.");
	}
}