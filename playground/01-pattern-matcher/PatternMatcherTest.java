import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PatternMatcherTest {

	@Test
	public void testMatch1() {
		assertTrue(PatternMatcher.match("abcdef", "de"));
	}

	@Test
	public void testMatch2() {
		assertTrue(PatternMatcher.match("abcdef", "d?"));
	}

	@Test
	public void testMatch3() {
		assertTrue(PatternMatcher.match("abcdef", "*ef"));
	}

	@Test
	public void testMatch4() {
		assertFalse(PatternMatcher.match("abcdef", "a?cd*g"));
	}
}