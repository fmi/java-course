import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PatternMatcherTest {

	@Test
	public void testMatch_WithoutSpecialChars() {
		assertTrue(PatternMatcher.match("abcdef", "de"));
	}

	@Test
	public void testMatch_WithSingleQuestionMark() {
		assertTrue(PatternMatcher.match("abcdef", "d?"));
	}

	@Test
	public void testMatch_WithSingleStarMark() {
		assertTrue(PatternMatcher.match("abcdef", "*ef"));
	}

	@Test
	public void testMatch_AllSpecialChars() {
		assertFalse(PatternMatcher.match("abcdef", "a?cd*g"));
	}
}