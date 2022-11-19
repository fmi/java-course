package bg.sofia.uni.fmi.mjt.myfitnesspal.diary;

import bg.sofia.uni.fmi.mjt.myfitnesspal.exception.UnknownFoodException;

import java.util.Collection;
import java.util.List;

public interface FoodDiary {

    /**
     * Adds a food to the diary.
     *
     * @param meal        the meal of the day.
     * @param foodName    the food name.
     * @param servingSize the serving size.
     * @return the food entry added.
     * @throws IllegalArgumentException if meal is null, foodName is null, empty or blank, or serving size is negative.
     * @throws UnknownFoodException if no nutrition info is available for the food with the specified name
     */
    FoodEntry addFood(Meal meal, String foodName, double servingSize) throws UnknownFoodException;

    /**
     * Returns all food entries.
     *
     * @return all food entries as an unmodifiable collection, in an undefined order.
     */
    Collection<FoodEntry> getAllFoodEntries();

    /**
     * Returns all food entries, sorted by protein content.
     *
     * @return all food entries as an unmodifiable collection, sorted by protein content, in ascending order.
     */
    List<FoodEntry> getAllFoodEntriesByProteinContent();

    /**
     * Returns the daily calories intake for the respective diary.
     *
     * @return the total calories intake for the respective daily diary, or 0.0, if no food has been added.
     */
    double getDailyCaloriesIntake();

    /**
     * Returns the total calories intake for the respective meal.
     *
     * @return the total calories intake for the respective meal. If no food has been added for this meal, returns 0.0.
     * @throws IllegalArgumentException if meal is null
     */
    double getDailyCaloriesIntakePerMeal(Meal meal);

}
