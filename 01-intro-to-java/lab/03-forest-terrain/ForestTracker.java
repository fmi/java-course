public class ForestTracker {

	private static final char FIRST_LEVEL_DENSITY = '1';
	private static final char SECOND_LEVEL_DENSITY = '2';
	private static final char THIRD_LEVEL_DENSITY = '3';
	private static final char FORTH_LEVEL_DENSITY = '4';
	
	private static final int CHANGE_PERIOD_IN_YEARS = 10;
	
	private static final int[][] NEIGHBOUR_INDEXES = { {-1, -1}, {-1, 0}, {-1, 1},
													   {0, -1}, {0, 1}, 
													   {1, -1}, {1, 0}, {1, 1} };

	public char[][] trackForestTerrain(char[][] terrain, int years) {
		if (years < CHANGE_PERIOD_IN_YEARS) {
			return terrain;
		}

		char[][] newTerrain = copyArray(terrain);
		int modificationsCount = years / CHANGE_PERIOD_IN_YEARS; 
		for (int i = 0; i < modificationsCount; i++) {
			newTerrain = modifyWoods(newTerrain);
		}

		return newTerrain;
	}

	private char[][] modifyWoods(char[][] terrain) {
		int rows = terrain.length;
		int columns = terrain[0].length;

		char[][] copy = copyArray(terrain);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (terrain[i][j] == FIRST_LEVEL_DENSITY) {
					copy[i][j] = SECOND_LEVEL_DENSITY;
				} else if (terrain[i][j] == SECOND_LEVEL_DENSITY) {
					copy[i][j] = THIRD_LEVEL_DENSITY;
				} else if(terrain[i][j] == FORTH_LEVEL_DENSITY && checkIfModificationNeeded(terrain, i, j)) {
					copy[i][j] = THIRD_LEVEL_DENSITY;
				} else if (terrain[i][j] == THIRD_LEVEL_DENSITY) {
					copy[i][j] = FORTH_LEVEL_DENSITY;
				}
			}
		}

		return copy;
	}

	private boolean checkIfModificationNeeded(char[][] terrain, int i, int j) {
		int rows = terrain.length;
		int columns = terrain[0].length;

		int minimumNumberOfFields = 3;
		int thirdDensityFieldsCounter = 0;
		for (int k = 0; k < NEIGHBOUR_INDEXES.length; k++) {
			int x = i + NEIGHBOUR_INDEXES[k][0];
			int y = j + NEIGHBOUR_INDEXES[k][1];

			if (checkIfPositionValid(x, y, rows, columns) && terrain[x][y] == FORTH_LEVEL_DENSITY) {
				thirdDensityFieldsCounter++;						
			}

			if (thirdDensityFieldsCounter == minimumNumberOfFields) {
				break;
			}
		}

		return thirdDensityFieldsCounter == minimumNumberOfFields;
	}

	private boolean checkIfPositionValid(int x, int y, int rows, int columns) {
		return (x >= 0 && x < rows && y >=0 && y < columns);
	}

	private char[][] copyArray(char[][] array) {
		char[][] copy = new char[array.length][array[0].length];
		
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				copy[i][j] = array[i][j];
				
			}
		}
		
		return copy;
	}
}
