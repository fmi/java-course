import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class SampleCounterTest {

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
}
