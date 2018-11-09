import static org.junit.Assert.assertArrayEquals;

import org.junit.Before;
import org.junit.Test;

public class SampleForestTrackerTest {

	private ForestTracker tracker;

	@Before
	public void setUp() {
		tracker = new ForestTracker();
	}

	@Test
	public void testTrack_ForestStaysTheSameForLessThan10Years() {
		char[][] forest = { {'S', '1', 'S', '1', 'R'},
							{'S', '2', '4', '4', '4'},
							{'1', '3', 'S', '3', '4'}, };

		assertArrayEquals(forest, tracker.trackForestTerrain(forest, -10));
		assertArrayEquals(forest, tracker.trackForestTerrain(forest, 5));
	}

	@Test
	public void testTrack_Density4BecomesDensity3AndOtherDensitiesChangeCorrectly() {
		char[][] forest = { {'R', 'R', 'S', '1', '4'},
							{'S', '2', '4', '4', '2'},
							{'1', '3', 'S', '3', '4'}, };

		char[][] expected = { {'R', 'R', 'S', '2', '4'},
							  {'S', '3', '4', '3', '3'},
							  {'2', '4', 'S', '4', '4'}, };

		assertArrayEquals(expected, tracker.trackForestTerrain(forest, 10));
		assertArrayEquals(expected, tracker.trackForestTerrain(forest, 15));
	}
}
