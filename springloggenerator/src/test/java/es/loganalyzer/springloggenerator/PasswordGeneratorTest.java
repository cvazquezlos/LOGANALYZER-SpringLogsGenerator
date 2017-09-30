package es.loganalyzer.springloggenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;

public class PasswordGeneratorTest {
    private static final Logger logger = LoggerFactory.getLogger(PasswordGeneratorTest.class);

	@Test
	public void checkRegionalChars(){
		PasswordGenerator tester = new PasswordGenerator(new Index(), new String[]{"12", "Medium", "false", "true", "false", "US / UK"});
		PasswordGenerator tester2 = new PasswordGenerator(new Index(), new String[]{"8", "Advanced", "true", "true", "true", "SP"});
		
		assertEquals("tester;regionChars must contain US / UK regional chars.", 0, tester.regionChars.size());
		logger.info("tester;regionChars must contain US / UK regional chars.");
		assertEquals("tester2;regionChars must contain SP regional chars.", 2, tester2.regionChars.size());
		logger.info("tester2;regionChars must contain SP regional chars.");
	}
	
	@Test
	public void checkUpdateValues(){
		PasswordGenerator tester = new PasswordGenerator(new Index(), new String[]{"12", "Medium", "false", "true", "false", "US / UK"});
		PasswordGenerator tester2 = new PasswordGenerator(new Index(), new String[]{"8", "Advanced", "true", "true", "true", "SP"});
		
		assertNotNull("tester;first condition;numChat must contain a valid int value.", tester.numChar);
		logger.info("tester;first condition;numChat must contain a valid int value.");
		assertNotNull("tester;second condition;secLevel must contain a valid String value.", tester.secLevel);
		logger.info("tester;second condition;secLevel must contain a valid String value.");
		assertNotNull("tester;third condition;specialChar must contain a valid boolean value.", tester.specialChar);
		logger.info("tester;third condition;specialChar must contain a valid boolean value.");
		assertNotNull("tester;fourth condition;numbers must contain a valid boolean value.", tester.numbers);
		logger.info("tester;fourth condition;numbers must contain a valid boolean value.");
		assertNotNull("tester;fifth condition;wantRepeated must contain a valid boolean value.", tester.wantRepeated);
		logger.info("tester;fifth condition;wantRepeated must contain a valid boolean value.");
		assertNotNull("tester;fifth condition;regionChar must contain a valid String value.", tester.regionChar);
		logger.info("tester;fifth condition;regionChar must contain a valid String value.");
		
		assertNotNull("tester2;first condition;numChat must contain a valid int value.", tester2.numChar);
		logger.info("tester2;first condition;numChat must contain a valid int value.");
		assertNotNull("tester2;second condition;secLevel must contain a valid String value.", tester2.secLevel);
		logger.info("tester2;second condition;secLevel must contain a valid String value.");
		assertNotNull("tester2;third condition;specialChar must contain a valid boolean value.", tester2.specialChar);
		logger.info("tester2;third condition;specialChar must contain a valid boolean value.");
		assertNotNull("tester2;fourth condition;numbers must contain a valid boolean value.", tester2.numbers);
		logger.info("tester2;fourth condition;numbers must contain a valid boolean value.");
		assertNotNull("tester2;fifth condition;wantRepeated must contain a valid boolean value.", tester2.wantRepeated);
		logger.info("tester2;fifth condition;wantRepeated must contain a valid boolean value.");
		assertNotNull("tester2;fifth condition;regionChar must contain a valid String value.", tester2.regionChar);
		logger.info("tester2;fifth condition;regionChar must contain a valid String value.");
	}
	
	@Test
	public void checkSecLevel(){
		PasswordGenerator tester = new PasswordGenerator(new Index(), new String[]{"12", "Medium", "false", "true", "false", "US / UK"});
		PasswordGenerator tester2 = new PasswordGenerator(new Index(), new String[]{"8", "Advanced", "true", "true", "true", "SP"});

		assertNotNull("tester;secLevel must have an int value in gradeLevel variable.", tester.gradeLevel);
		logger.info("tester;secLevel must have an int value in gradeLevel variable.");
		assertNotNull("tester2;secLevel must have an int value in gradeLevel variable.", tester2.gradeLevel);
		logger.info("tester2;secLevel must have an int value in gradeLevel variable.");
		
	}
	
	@Test
	public void checkCreatePassword(){
		PasswordGenerator tester = new PasswordGenerator(new Index(), new String[]{"12", "Medium", "false", "true", "false", "US / UK"});
		PasswordGenerator tester2 = new PasswordGenerator(new Index(), new String[]{"8", "Advanced", "true", "true", "true", "SP"});
		
		String password = tester.createPassword();
		String password2 = tester2.createPassword();
		
		assertTrue("tester;Password generated and its value is " + password, true);
		logger.info("tester;Password generated and its value is " + password);
		assertTrue("tester2;Password generated and its value is " + password2, true);
		logger.info("tester2;Password generated and its value is " + password2);
	}
	
}
