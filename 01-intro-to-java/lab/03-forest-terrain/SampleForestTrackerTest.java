import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class SampleForestTrackerTestSuite {

	private static ForestTracker tracker = new ForestTracker();
	
	@Test
	public void testIfForestStaysTheSameForLessThan10Years() {
		char[][] forest = {{'S', '1', 'S', '1', 'R'},
						  {'S', '2', '4', '4', '4'},
						  {'1', '3', 'S', '3', '4'}};

		Assert.assertTrue(Arrays.deepEquals(forest, tracker.trackForestTerrain(forest, -10)));
		Assert.assertTrue(Arrays.deepEquals(forest, tracker.trackForestTerrain(forest, 5)));
	}
	
	@Test
	public void testIfDensity4BecomesDensity3AndOtherDensitiesChangeCorrectly() {
		char[][] originalForest = {{'R', 'R', 'S', '1', '4'},
								   {'S', '2', '4', '4', '2'},
								   {'1', '3', 'S', '3', '4'}};
		
		char[][] expectedForest = {{'R', 'R', 'S', '2', '4'},
						  		   {'S', '3', '4', '3', '3'},
						  		   {'2', '4', 'S', '4', '4'}};
		
		Assert.assertTrue(Arrays.deepEquals(expectedForest, tracker.trackForestTerrain(originalForest, 10)));
		Assert.assertTrue(Arrays.deepEquals(expectedForest, tracker.trackForestTerrain(originalForest, 15)));
	}
}
