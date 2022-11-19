package bg.sofia.uni.fmi.mjt.myfitnesspal.diary;

import bg.sofia.uni.fmi.mjt.myfitnesspal.nutrition.NutritionInfo;

public record FoodEntry(String food, double servingSize, NutritionInfo nutritionInfo) {

    public FoodEntry {
        if (food == null || food.isBlank()) {
            throw new IllegalArgumentException("Food cannot be null or blank");
        }

        if (nutritionInfo == null) {
            throw new IllegalArgumentException("Nutrition info cannot be null");
        }
    }

}
