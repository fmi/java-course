import static org.junit.Assert.assertArrayEquals;

import org.junit.Before;
import org.junit.Test;

public class ForestTrackerTest {

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

	@Test
	public void testTrack_EachDensityChangesCorrectly() {
		char[][] ones = { {'1', '1', '1'},
						  {'1', '1', '1'},
						  {'1', '1', '1'}, };

		char[][] twos = { {'2', '2', '2'},
						  {'2', '2', '2'},
						  {'2', '2', '2'}, };

		char[][] threes = { {'3', '3', '3'},
							{'3', '3', '3'},
							{'3', '3', '3'}, };

		char[][] fours = { {'4', '4', '4'},
						   {'4', '4', '4'},
						   {'4', '4', '4'}, };

		assertArrayEquals(ones, tracker.trackForestTerrain(ones, -10));
		assertArrayEquals(ones, tracker.trackForestTerrain(ones, 5));
		assertArrayEquals(twos, tracker.trackForestTerrain(ones, 10));

		assertArrayEquals(twos, tracker.trackForestTerrain(twos, -10));
		assertArrayEquals(twos, tracker.trackForestTerrain(twos, 5));
		assertArrayEquals(threes, tracker.trackForestTerrain(twos, 10));

		assertArrayEquals(threes, tracker.trackForestTerrain(threes, -10));
		assertArrayEquals(threes, tracker.trackForestTerrain(threes, 5));
		assertArrayEquals(fours, tracker.trackForestTerrain(threes, 10));

		assertArrayEquals(fours, tracker.trackForestTerrain(fours, -10));
		assertArrayEquals(fours, tracker.trackForestTerrain(fours, 5));
		assertArrayEquals(threes, tracker.trackForestTerrain(fours, 10));
		assertArrayEquals(fours, tracker.trackForestTerrain(fours, 40));
	}

	@Test
	public void testTrack_StaticFieldsStayTheSame() {
		char[][] river = { {'R', 'R', 'R'},
						   {'R', 'R', 'R'},
						   {'R', 'R', 'R'}, };

		char[][] rock = { {'S', 'S', 'S'},
						  {'S', 'S', 'S'},
						  {'S', 'S', 'S'}, };

		assertArrayEquals(river, tracker.trackForestTerrain(river, 10));
		assertArrayEquals(rock, tracker.trackForestTerrain(rock, 10));
	}

	@Test
	public void testTrack_Density4StaysTheSame() {
		char[][] forest = { {'R', 'R', 'S'},
							{'4', '4', 'R'},
							{'S', '4', 'S'}, };

		assertArrayEquals(forest, tracker.trackForestTerrain(forest, -5));
		assertArrayEquals(forest, tracker.trackForestTerrain(forest, 10));
		assertArrayEquals(forest, tracker.trackForestTerrain(forest, 20));
	}

	@Test
	public void testTrack_MixedDensityWithout4ChangesCorrectly() {
		char[][] forest = { {'R', '2', '1'},
							{'4', '4', '3'},
							{'S', '2', '3'}, };

		char[][] expected = { {'R', '3', '2'},
							  {'4', '4', '4'},
							  {'S', '3', '4'}, };

		assertArrayEquals(expected, tracker.trackForestTerrain(forest, 10));
		assertArrayEquals(expected, tracker.trackForestTerrain(forest, 15));
	}

	@Test
	public void testTrack_MultupleNeighbourDensities4ChangeCorrectly() {
		char[][] forest = { {'R', '2', '1'},
							{'4', '4', '3'},
							{'4', '4', '1'}, };

		char[][] expected = { {'R', '3', '2'},
							  {'3', '3', '4'},
							  {'3', '3', '2'}, };

		assertArrayEquals(expected, tracker.trackForestTerrain(forest, 10));
		assertArrayEquals(expected, tracker.trackForestTerrain(forest, 15));
	}
}
