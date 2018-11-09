import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class AnagramTest {

	private Anagram anagram;

	@Before
	public void setUp() {
		anagram = new Anagram();
	}

	@Test
	public void testIsAnagram_PlainAnagrams() {
		assertTrue(anagram.isAnagram("night thing"));
	}

	@Test
	public void testIsAnagram_MissingLetter() {
		assertFalse(anagram.isAnagram("tired dire"));
	}

	@Test
	public void testIsAnagram_IgnoreCaseAndSpecialChars() {
		assertTrue(anagram.isAnagram("eaT ate$"));
	}

	@Test
	public void testIsAnagram_IgnoreCase() {
		assertTrue(anagram.isAnagram("ARc CAr"));
	}

	@Test
	public void testIsAnagram_NoAnagramsSameSize() {
		assertFalse(anagram.isAnagram("card cars"));
	}

	@Test
	public void testIsAnagram_SameLettersDifferentCount() {
		assertFalse(anagram.isAnagram("beer beere"));
	}

	@Test
	public void testIsAnagram_MissingLetterLeft() {
		assertFalse(anagram.isAnagram("car arcs"));
	}

	@Test
	public void testIsAnagram_SameWords() {
		assertTrue(anagram.isAnagram("same same"));
	}

	@Test
	public void testIsAnagram_BothSpecial() {
		assertTrue(anagram.isAnagram("$_cat_цветко %_act_$"));
	}

	@Test
	public void testIsAnagram_LeftSpecialOnly() {
		assertFalse(anagram.isAnagram("!@ a"));
	}

	@Test
	public void testIsAnagram_RightSpecialOnly() {
		assertFalse(anagram.isAnagram("a **юя&"));
	}

	@Test
	public void testIsAnagram_LeftWithRepeatingLetters() {
		assertFalse(anagram.isAnagram("food fd"));
	}

	@Test
	public void testIsAnagram_RightWithRepeatingLetters() {
		assertFalse(anagram.isAnagram("fmi aofoameuiue"));
	}
}
