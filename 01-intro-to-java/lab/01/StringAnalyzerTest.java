import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringAnalyzerTest {

	@Test
	public void test1() {
		assertEquals(3, StringAnalyzer.getPlateauLength("abbabcaaaccdcdcdc"));
	}

	@Test
	public void test2() {
		assertEquals(5, StringAnalyzer.getPlateauLength("abbabcaaaccdddddcdcdc"));
	}

	@Test
	public void test3() {
		assertEquals(1, StringAnalyzer.getPlateauLength("a"));
	}

	@Test
	public void test4() {
		assertEquals(5, StringAnalyzer.getPlateauLength("aaaaa"));
	}

	@Test
	public void test5() {
		assertEquals(0, StringAnalyzer.getPlateauLength(""));
	}
}
