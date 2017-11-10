package com.jayway.controller;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TransferTest {

	static Validator validator;
	Transfer transfer;

	@BeforeClass
	public static void beforeClass() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void shouldAllowZeroAmount() {
		System.out.println("Starting shouldAllowZeroAmount() method testing...");
		transfer = new Transfer(0, 0, 0);

		Set<ConstraintViolation<Transfer>> constraintViolations = validator.validate(transfer);

		assertThat(constraintViolations.size(), is(0));
		System.out.println("shouldAllowZeroAmount() method testing finished.");
	}

	@Test
	public void shouldNotAllowNegativeAmount() {
		System.out.println("Starting shouldNotAllowNegativeAmount() method testing...");
		transfer = new Transfer(0, 0, -1);

		Set<ConstraintViolation<Transfer>> constraintViolations = validator.validate(transfer);

		assertThat(constraintViolations.size(), is(1));
		assertThat(constraintViolations.iterator().next().getMessage(), is("Amount must be >= 0"));
		System.out.println("shouldNotAllowNegativeAmount() method testing finished.");
	}

	@Test
	public void shouldHaveFromAccountNumber() {
		System.out.println("Starting shouldHaveFromAccountNumber() method testing...");
		transfer = new Transfer(null, 0, 0);

		Set<ConstraintViolation<Transfer>> constraintViolations = validator.validate(transfer);

		assertThat(constraintViolations.size(), is(1));
		assertThat(constraintViolations.iterator().next().getMessage(), is("From account number must be set"));
		System.out.println("shouldHaveFromAccountNumber() method testing finished.");
	}

	@Test
	public void shouldHaveToAccountNumber() {
		System.out.println("Starting shouldHaveToAccountNumber() method testing...");
		transfer = new Transfer(0, null, 0);

		Set<ConstraintViolation<Transfer>> constraintViolations = validator.validate(transfer);

		assertThat(constraintViolations.size(), is(1));
		assertThat(constraintViolations.iterator().next().getMessage(), is("To account number must be set"));
		System.out.println("shouldHaveToAccountNumber() method testing finished.");
	}
}