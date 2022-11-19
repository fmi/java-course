package bg.sofia.uni.fmi.mjt.myfitnesspal.nutrition;

public enum MacroNutrient {
    PROTEIN(4),
    CARBOHYDRATE(4),
    FAT(9);

    final int calories;

    MacroNutrient(int calories) {
        this.calories = calories;
    }

}
