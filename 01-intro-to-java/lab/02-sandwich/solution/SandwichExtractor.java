import java.util.Arrays;

public class SandwichExtractor {

    public static String[] extractIngredients(String sandwich) {
        String[] breadSplit = sandwich.split("bread");
        if (breadSplit.length != 3) {
            return new String[]{};
        }

        String[] ingredients = breadSplit[1].split("-");
        int size = 0;
        for (String ingredient : ingredients) {
            if (!ingredient.equals("olives")) {
                size++;
            }
        }

        String[] result = new String[size];
        int index = 0;
        for (String ingredient : ingredients) {
            if (!ingredient.equals("olives")) {
                result[index++] = ingredient;
            }
        }
        Arrays.sort(result);

        return result;
    }

}
