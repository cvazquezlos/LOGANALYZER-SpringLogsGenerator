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
		
		logger.info("tester;regionChars must contain US / UK regional chars.");
		assertEquals("tester;regionChars must contain US / UK regional chars.", 0, tester.regionChars.size());
		assertEquals("tester2;regionChars must contain SP regional chars.", 2, tester2.regionChars.size());
	}
	
	@Test
	public void checkUpdateValues(){
		PasswordGenerator tester = new PasswordGenerator(new Index(), new String[]{"12", "Medium", "false", "true", "false", "US / UK"});
		PasswordGenerator tester2 = new PasswordGenerator(new Index(), new String[]{"8", "Advanced", "true", "true", "true", "SP"});
		
		assertNotNull("tester;first condition;numChat must contain a valid int value.", tester.numChar);
		assertNotNull("tester;second condition;secLevel must contain a valid String value.", tester.secLevel);
		assertNotNull("tester;third condition;specialChar must contain a valid boolean value.", tester.specialChar);
		assertNotNull("tester;fourth condition;numbers must contain a valid boolean value.", tester.numbers);
		assertNotNull("tester;fifth condition;wantRepeated must contain a valid boolean value.", tester.wantRepeated);
		assertNotNull("tester;fifth condition;regionChar must contain a valid String value.", tester.regionChar);
		
		assertNotNull("tester2;first condition;numChat must contain a valid int value.", tester2.numChar);
		assertNotNull("tester2;second condition;secLevel must contain a valid String value.", tester2.secLevel);
		assertNotNull("tester2;third condition;specialChar must contain a valid boolean value.", tester2.specialChar);
		assertNotNull("tester2;fourth condition;numbers must contain a valid boolean value.", tester2.numbers);
		assertNotNull("tester2;fifth condition;wantRepeated must contain a valid boolean value.", tester2.wantRepeated);
		assertNotNull("tester2;fifth condition;regionChar must contain a valid String value.", tester2.regionChar);
	}
	
	@Test
	public void checkSecLevel(){
		PasswordGenerator tester = new PasswordGenerator(new Index(), new String[]{"12", "Medium", "false", "true", "false", "US / UK"});
		PasswordGenerator tester2 = new PasswordGenerator(new Index(), new String[]{"8", "Advanced", "true", "true", "true", "SP"});

		assertNotNull("tester;secLevel must have an int value in gradeLevel variable.", tester.gradeLevel);
		assertNotNull("tester2;secLevel must have an int value in gradeLevel variable.", tester2.gradeLevel);
		
	}
	
	@Test
	public void checkCreatePassword(){
		PasswordGenerator tester = new PasswordGenerator(new Index(), new String[]{"12", "Medium", "false", "true", "false", "US / UK"});
		PasswordGenerator tester2 = new PasswordGenerator(new Index(), new String[]{"8", "Advanced", "true", "true", "true", "SP"});
		
		String password = tester.createPassword();
		String password2 = tester2.createPassword();
		
		assertTrue("tester;Password generated and its value is " + password, true);
		assertTrue("tester2;Password generated and its value is " + password2, true);
	}
	
}
