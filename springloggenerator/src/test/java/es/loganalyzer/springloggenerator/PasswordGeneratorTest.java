package es.loganalyzer.springloggenerator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PasswordGeneratorTest {

	@Test
	public void regionalCharsTest(){
		PasswordGenerator tester = new PasswordGenerator(new Index(), new String[]{"2", "Medium", "false", "true", "false", "US / UK"});
		
		assertEquals("regionChars must contain US / UK regional chars.", 0, tester.regionChars.size());
	}
	
}
