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

	@Test
	public void testMatch5() {
		assertTrue(PatternMatcher.match("a", "?"));
	}

	@Test
	public void testMatch6() {
		assertTrue(PatternMatcher.match("a", "*"));
	}

	@Test
	public void testMatch7() {
		assertTrue(PatternMatcher.match("a", "a"));
	}

	@Test
	public void testMatch8() {
		assertTrue(PatternMatcher.match("abcdefaaaba", "*aaa??"));
	}

	@Test
	public void testMatch9() {
		assertFalse(PatternMatcher.match("abcd", "abcde"));
	}

	@Test
	public void testMatch10() {
		assertFalse(PatternMatcher.match("abcd", "abcd?"));
	}

	@Test
	public void testMatch11() {
		assertTrue(PatternMatcher.match("abcd", "ab*cd*"));
	}

	@Test
	public void testMatch12() {
		assertTrue(PatternMatcher.match("abcdef", "**?*"));
	}

	@Test
	public void testMatch13() {
		assertTrue(PatternMatcher.match("abcdef", "************************"));
	}

	@Test
	public void testMatch14() {
		assertTrue(PatternMatcher.match("%&%$%^&^hjgewkjhdedhhjdewewghjgdewhjgdewhjg", "%??$*jg"));
	}

	@Test
	public void testMatch15() {
		assertFalse(PatternMatcher.match("abc", "aabc"));
	}

	@Test
	public void testMatch16() {
		assertFalse(PatternMatcher.match("ab", "???"));
	}

	@Test
	public void testMatch17() {
		assertTrue(PatternMatcher.match("aaabbb", "b"));
	}

	@Test
	public void testMatch18() {
		assertFalse(PatternMatcher.match("abcdefabcdefdefabcr", "bcrf"));
	}

	@Test
	public void testMatch19() {
		assertTrue(PatternMatcher.match("abcdef^%!&$%@$s5", "@$??"));
	}

	@Test
	public void testMatch20() {
		assertTrue(PatternMatcher.match("$ABC&AB&&ABC0BC", "ABC"));
	}

	@Test
	public void testMatch21() {
		assertTrue(PatternMatcher.match("abc", "abc*"));
	}

	@Test
	public void testMatch22() {
		assertFalse(PatternMatcher.match("abc", "abc+"));
	}

	@Test
	public void testMatch23() {
		assertTrue(PatternMatcher.match("%&^%&^%21378217321uwqdiuyduqwyd812e8732dyutqwtdwq7687e632876ewdgeyugwdyewg",
				"*"));
	}

	@Test
	public void testMatch24() {
		assertTrue(PatternMatcher.match("a77%%1*??de*a45", "7*?1???de"));
	}

	@Test
	public void testMatch25() {
		assertTrue(PatternMatcher.match("abfwedeabrwckwehf32768sqwhabdabdababab876872jdjgabcdeg", "*abc"));
	}
}
