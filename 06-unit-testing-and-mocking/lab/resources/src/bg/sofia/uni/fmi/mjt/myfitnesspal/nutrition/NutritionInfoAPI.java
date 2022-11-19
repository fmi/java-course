package bg.sofia.uni.fmi.mjt.myfitnesspal.nutrition;

import bg.sofia.uni.fmi.mjt.myfitnesspal.exception.UnknownFoodException;

public interface NutritionInfoAPI {

    /**
     * Returns the nutrition info for a food. It contains the macronutrient composition of the food:
     * the carbohydrates, fats and proteins (in grams per 100 grams of product). Those grams are non-negative
     * numbers and their sum always equals 100.
     *
     * @param foodName    the food name.
     * @return the nutrition info for a food.
     * @throws IllegalArgumentException if food name is null, empty or blank.
     * @throws UnknownFoodException if no nutrition info is available for the food with the specified name
     */
    NutritionInfo getNutritionInfo(String foodName) throws UnknownFoodException;

}
