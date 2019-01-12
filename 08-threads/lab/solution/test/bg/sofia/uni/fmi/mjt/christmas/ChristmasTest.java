package bg.sofia.uni.fmi.mjt.christmas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

public class ChristmasTest {

	static Christmas c10 = null;
	static Christmas c100 = null;
	static Christmas c1000 = null;
	static Christmas c10000 = null;

	@BeforeClass
	public static void setUp() {
		c10 = new Christmas(new Workshop(), 10, 200);
		c10.celebrate();

		c100 = new Christmas(new Workshop(), 100, 1000);
		c100.celebrate();

		c1000 = new Christmas(new Workshop(), 1000, 500);
		c1000.celebrate();

		c10000 = new Christmas(new Workshop(), 10000, 1000);
		c10000.celebrate();
	}

	@Test(timeout = 1000)
	public void testWishCount10() {
		assertEquals(c10.getWorkshop().getWishCount(), 10);
	}

	@Test(timeout = 1000)
	public void testWishCount100() {
		assertEquals(100, c100.getWorkshop().getWishCount());
	}

	@Test(timeout = 1000)
	public void testWishCount1000() {
		assertEquals(1000, c1000.getWorkshop().getWishCount());
	}

	@Test(timeout = 1000)
	public void testWishCount10000() {
		assertEquals(10000, c10000.getWorkshop().getWishCount());
	}

	@Test(timeout = 1000)
	public void testTotalCrafted() {

		int totalCrafted = Arrays.stream(c10.getWorkshop().getElves())
							.mapToInt(Elf::getTotalGiftsCrafted)
							.sum();

		assertEquals(10, totalCrafted);
	}

	@Test(timeout = 1000)
	public void testTotalCrafted100() {
		int totalCrafted = Arrays.stream(c100.getWorkshop().getElves())
							.mapToInt(Elf::getTotalGiftsCrafted)
							.sum();

		assertEquals(100, totalCrafted);
	}

	@Test(timeout = 1000)
	public void testTotalCrafted1000() {
		int totalCrafted = Arrays.stream(c1000.getWorkshop().getElves())
							.mapToInt(Elf::getTotalGiftsCrafted)
							.sum();

		assertEquals(1000, totalCrafted);
	}

	@Test(timeout = 1000)
	public void testElfBacklogDistrubution() {
		double max = Arrays.stream(c1000.getWorkshop().getElves())
						.mapToDouble(Elf::getTotalGiftsCrafted)
						.max()
						.getAsDouble();

		double min = Arrays.stream(c1000.getWorkshop().getElves())
						.mapToDouble(Elf::getTotalGiftsCrafted)
						.min()
						.getAsDouble();

		assertTrue("One elf crafted " + max + " items while other just : " + min, ((min * 2) > max));
	}

}
