package appSpring.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class ResourceTest {

	private static final Logger logger = LogManager.getLogger(ResourceTest.class.getName());
	
	Resource tester = new Resource("Python Data Science Handbook", "Jake Vanderplas", "O'REILLY",
			"The book introduces the core libraries essential for working with data in Python");

	@Test
	public void getAuthor() {
		System.out.println("Starting getAuthor() test...");
		assertEquals("Author must be Jake Vanderplas.", "Jake Vanderplas", tester.getAutor());
		logger.info("Author must be Jake Vanderplas.");
		assertNotEquals("Author can't be Lucas.", "Lucas", tester.getAutor());
		logger.info("Author can't be Lucas.");
		System.out.println("getAuthor() test finished.");
	}
	
	@Test
	public void setEditorial() {
		System.out.println("Starting setEditorial() test...");
		tester.setEditorial("O'REILLY");
		assertEquals("Editorial must be O'REILLY.", "O'REILLY", tester.getEditorial());
		logger.info("Editorial must be O'REILLY.");
		System.out.println("setEditorial() test finished.");
	}
}