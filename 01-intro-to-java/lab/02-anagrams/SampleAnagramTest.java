import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class SampleAnagramTest {

	private Anagram anagram;

	@Before
	public void setUp() {
		anagram = new Anagram();
	}

	@Test
	public void testAnagrams_PlainAnagrams() {
		assertTrue(anagram.isAnagram("night thing"));
	}

	@Test
	public void testAnagrams_MissingLetter() {
		assertFalse(anagram.isAnagram("tired dire"));
	}

	@Test
	public void testAnagrams_IgnoreCaseAndSpecialChars() {
		assertTrue(anagram.isAnagram("eaT ate$"));
	}

	@Test
	public void testAnagrams_SameLettersDifferentCount() {
		assertFalse(anagram.isAnagram("beer beere"));
	}
}
