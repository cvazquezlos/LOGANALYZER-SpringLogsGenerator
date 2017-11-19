package com.example.bookstore.basic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.Before;
import org.junit.Test;

import com.example.bookstore.dao.DummyDao;

public class BasicUnitTest {

	private static final Logger logger = LogManager.getLogger(BasicUnitTest.class.getName());

	private List<String> database;
	private DummyDao dummyDao;

	@Before
	public void dataSetup() {
		database = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			database.add("test" + i);
		}
		dummyDao = new DummyDao(database);
	}

	@Test
	public void testDeleteQuery() {
		System.out.println("Starting testDeleteQuery() method test...");
		assertEquals(1, dummyDao.find("test0").size());
		logger.info("Database must contain any target value.");
		dummyDao.delete("test0");
		assertEquals(19, database.size());
		logger.info("After deleting info, database size have to be 19.");
		assertEquals(0, dummyDao.find("test0").size());
		logger.info("Database can't contain target value.");
		System.out.println("testDeleteQuery() method test finished...");
	}

	@Test
	public void testAddQuery() {
		System.out.println("Starting testAddQuery() method test...");
		assertEquals(0, dummyDao.find("test20").size());
		logger.info("Database can't contain target value.");
		dummyDao.add("test20");
		assertEquals(21, database.size());
		logger.info("After adding info, database size have to be 21.");
		assertEquals(1, dummyDao.find("test20").size());
		logger.info("Database must contain target value.");
		System.out.println("testAddQuery() method test finished...");
	}

	@Test
	public void testFindQuery() {
		System.out.println("Starting testFindQuery() method test...");
		List<String> results = dummyDao.find("2");
		assertEquals(2, results.size());
		for (String result : results) {
			assertTrue(result.equals("test2") || result.equals("test12"));
		}
		logger.info("Database must find target value.");
		System.out.println("testFindQuery() method test finished...");
	}
}
