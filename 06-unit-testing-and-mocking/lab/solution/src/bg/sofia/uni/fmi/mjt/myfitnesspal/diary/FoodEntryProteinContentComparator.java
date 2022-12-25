package bg.sofia.uni.fmi.mjt.myfitnesspal.diary;

import java.util.Comparator;

public class FoodEntryProteinContentComparator implements Comparator<FoodEntry> {

    @Override
    public int compare(FoodEntry o1, FoodEntry o2) {
        return Double.compare(
            o1.servingSize() * o1.nutritionInfo().proteins(), // bug: exchanged o1 and o2
            o2.servingSize() * o2.nutritionInfo().proteins()
        );
    }

}
