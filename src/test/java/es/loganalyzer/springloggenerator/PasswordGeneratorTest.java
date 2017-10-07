package es.loganalyzer.springloggenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.Test;

public class PasswordGeneratorTest {

	private static final Logger logger = LogManager.getLogger(PasswordGeneratorTest.class.getName());

	@Test
	public void checkRegionalChars() {
		PasswordGenerator tester = new PasswordGenerator(new Index(),
				new String[] { "12", "Medium", "false", "true", "false", "US / UK" });
		PasswordGenerator tester2 = new PasswordGenerator(new Index(),
				new String[] { "8", "Advanced", "true", "true", "true", "SP" });

		assertEquals("Tester. regionChars must contain US / UK regional chars.", 0, tester.regionChars.size());
		logger.info("Tester. regionChars must contain US / UK regional chars.");
		assertEquals("Tester2. regionChars must contain SP regional chars.", 2, tester2.regionChars.size());
		logger.info("Tester2. regionChars must contain SP regional chars.");
	}

	@Test
	public void checkUpdateValues() {
		PasswordGenerator tester = new PasswordGenerator(new Index(),
				new String[] { "12", "Medium", "false", "true", "false", "US / UK" });
		PasswordGenerator tester2 = new PasswordGenerator(new Index(),
				new String[] { "8", "Advanced", "true", "true", "true", "SP" });

		assertNotNull("Tester. First condition. numChat must contain a valid int value.", tester.numChar);
		logger.info("Tester. First condition. numChat must contain a valid int value.");
		assertNotNull("Tester. Second condition. secLevel must contain a valid String value.", tester.secLevel);
		logger.info("Tester. Second condition. secLevel must contain a valid String value.");
		assertNotNull("Tester. Third condition. specialChar must contain a valid boolean value.", tester.specialChar);
		logger.info("Tester. Third condition. specialChar must contain a valid boolean value.");
		assertNotNull("Tester. Fourth condition. numbers must contain a valid boolean value.", tester.numbers);
		logger.info("Tester. Fourth condition. numbers must contain a valid boolean value.");
		assertNotNull("Tester. Fifth condition. wantRepeated must contain a valid boolean value.", tester.wantRepeated);
		logger.info("Tester. Fifth condition. wantRepeated must contain a valid boolean value.");
		assertNotNull("Tester. Sixth condition. regionChar must contain a valid String value.", tester.regionChar);
		logger.info("Tester. Sixth condition. regionChar must contain a valid String value.");

		assertNotNull("Tester2. First condition. numChat must contain a valid int value.", tester2.numChar);
		logger.info("Tester2. First condition. numChat must contain a valid int value.");
		assertNotNull("Tester2. Second condition. secLevel must contain a valid String value.", tester2.secLevel);
		logger.info("Tester2. Second condition. secLevel must contain a valid String value.");
		assertNotNull("Tester2. Third condition. specialChar must contain a valid boolean value.", tester2.specialChar);
		logger.info("Tester2. Third condition. specialChar must contain a valid boolean value.");
		assertNotNull("Tester2. Fourth condition. numbers must contain a valid boolean value.", tester2.numbers);
		logger.info("Tester2. Fourth condition. numbers must contain a valid boolean value.");
		assertNotNull("Tester2. Fifth condition. wantRepeated must contain a valid boolean value.",
				tester2.wantRepeated);
		logger.info("Tester2. Fifth condition. wantRepeated must contain a valid boolean value.");
		assertNotNull("Tester2. Sixth condition. regionChar must contain a valid String value.", tester2.regionChar);
		logger.info("Tester2. Sixth condition. regionChar must contain a valid String value.");
	}

	@Test
	public void checkSecLevel() {
		PasswordGenerator tester = new PasswordGenerator(new Index(),
				new String[] { "12", "Medium", "false", "true", "false", "US / UK" });
		PasswordGenerator tester2 = new PasswordGenerator(new Index(),
				new String[] { "8", "Advanced", "true", "true", "true", "SP" });

		assertNotNull("Tester. secLevel must have an int value in gradeLevel variable.", tester.gradeLevel);
		logger.info("Tester. secLevel must have an int value in gradeLevel variable.");
		assertNotNull("Tester2. secLevel must have an int value in gradeLevel variable.", tester2.gradeLevel);
		logger.info("Tester2. secLevel must have an int value in gradeLevel variable.");

	}

	@Test
	public void checkCreatePassword() {
		PasswordGenerator tester = new PasswordGenerator(new Index(),
				new String[] { "12", "Medium", "false", "true", "false", "US / UK" });
		PasswordGenerator tester2 = new PasswordGenerator(new Index(),
				new String[] { "8", "Advanced", "true", "true", "true", "SP" });

		String password = tester.createPassword();
		String password2 = tester2.createPassword();

		assertTrue("Tester. Password generated and its value is " + password + ".", true);
		logger.info("Tester. Password generated and its value is " + password + ".");
		assertTrue("Tester2. Password generated and its value is " + password2 + ".", true);
		logger.info("Tester2. Password generated and its value is " + password2 + ".");
	}

}
