import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CounterTest {

	private Counter counter;

	@Before
	public void setUp() {
		counter = new Counter();
	}

	@Test
	public void testCountTriples_WithSingleTriple() {
		assertEquals(1, counter.countTriples("aBBBcdee"));
	}

	@Test
	public void testCountTriples_WithThreeTriples() {
		assertEquals(3, counter.countTriples("AAAbCCCCdef"));
	}

	@Test
	public void testCountTriples_WithoutTriples() {
		assertEquals(0, counter.countTriples("a"));
	}

	@Test
	public void testCountTriples_WithEmptyString() {
		assertEquals(0, counter.countTriples(""));
	}

	@Test
	public void testCountTriples_WithCouples() {
		assertEquals(0, counter.countTriples("aabbccddaabaa"));
	}

	@Test
	public void testCountTriples_Hidden3() {
		assertEquals(2, counter.countTriples("BbBBBB"));
	}

	@Test
	public void testCountTriples_WithNumbers() {
		assertEquals(1, counter.countTriples("122abCCC2"));
	}

	@Test
	public void testCountTriples_WithSpecialChars() {
		assertEquals(4, counter.countTriples("abCCC!-)FFFFFab"));
	}

	@Test
	public void testCountTriples_WithTriplesInStartAndEnd() {
		assertEquals(3, counter.countTriples("AAAAbCCC"));
	}

	@Test
	public void testCountTriples_WithSpecialCharInEnd() {
		assertEquals(6, counter.countTriples("AAABBBcccaaaBbBcccDDD#"));
	}

	@Test
	public void testCountTriples_WithAllWorldTriple() {
		assertEquals(11, counter.countTriples("AAAAAAAAAAAAA"));
	}
}
