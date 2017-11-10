package appSpring.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class UserTest {

	private static final Logger logger = LogManager.getLogger(UserTest.class.getName());
	
	User tester = new User("cvazquezlos", "1234", "0001", "Carlos", "Vázquez",
			"Losada", "c.vazquezlosada@gmail.com", "656565066", "ROLE_ADMIN");
	
	@Test
	public void getFirstName() {
		System.out.println("Starting getFirstName() test...");
		assertEquals("Firstname must be Carlos", "Carlos", tester.getFirstName());
		logger.info("Firstname must be Carlos");
		assertNotEquals("Firstname can't be Lucas", "Lucas", tester.getFirstName());
		logger.info("Firstname can't be Lucas");
		System.out.println("getFirstName() test finished.");
	}
	
	@Test
	public void setRoles() {
		System.out.println("Starting setRoles() test...");
		tester.setRoles(new ArrayList<>());
		assertEquals("Roles of user must be empty", 0, tester.getRoles().size());
		logger.info("Roles of user must be empty");
		System.out.println("setRoles() test finished.");
	}
}