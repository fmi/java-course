package bg.sofia.uni.fmi.mjt.restaurant;

import java.util.Random;

public enum Meal {

	PIZZA("Pizza", 20), MUSAKA("Musaka", 30), SALAD("Salad", 5), SPAGHETTI("Spaghetti", 25);

	private static final Meal[] ALL_MEALS = Meal.values();
	private static final Random MEAL_RANDOM = new Random();

	private final String name;
	private final int cookingTime;

	private Meal(String type, int cookingTime) {
		this.name = type;
		this.cookingTime = cookingTime;
	}

	public String getName() {
		return name;
	}

	public int getCookingTime() {
		return cookingTime;
	}

	public static Meal chooseFromMenu() {
		return ALL_MEALS[MEAL_RANDOM.nextInt(ALL_MEALS.length)];
	}

}
