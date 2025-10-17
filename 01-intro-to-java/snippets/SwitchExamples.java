import java.util.Arrays;

public class SwitchExamples {

    public static void main(String[] args) {
        String foodForToday = "RAMEN";
        System.out.println(oldSwitchFoodGrader(foodForToday));
        System.out.println(java15FoodGrader(foodForToday));

        System.out.println(switchPatternMatchingStringConvert("Hello"));
        System.out.println(switchPatternMatchingStringConvert(25));
        System.out.println(switchPatternMatchingStringConvert(new char[] {'a', 'b', 'c'}));
        System.out.println(switchPatternMatchingStringConvert(null));

        switchPatternMatchingPrimitiveSelector();
    }

    private static String oldSwitchFoodGrader(String food) {
        String grade;
        switch (food) {
            case "PIZZA":
                grade = "NICE!";
                break;
            case "RAMEN":
                grade = "OOOH FANCY!";
                break;
            case "SNAILS":
                grade = "ARE YOU OUT OF YOUR MIND?!?";
                break;
            default:
                grade = "IF YOU REALLY WANT TO...";
        }
        return grade;
    }

    private static String java15FoodGrader(String food) {
        var grade = switch (food) {
            case "PIZZA" -> "NICE!";
            case "RAMEN" -> "OOOH FANCY!";
            case "SNAILS" -> "ARE YOU OUT OF YOUR MIND?!?";
            default -> "IF YOU REALLY WANT TO...";
        };
        return grade;
    }

    // Pattern matching in switch (since Java 21)
    private static String switchPatternMatchingStringConvert(Object inputObject) {
        return switch (inputObject) {
            case char[] charArray -> Arrays.toString(charArray);
            case Integer i -> "Integer: " + i;
            case String _ ->
                "Are you really trying to convert a string to a string?"; // (since Java 22) Unnamed variable - we won't use it
                                                                          // but instead scold the programmer.
            case null -> "It's a null reference"; // able to handle null references
            default -> "Unknown type: " + inputObject;
        };
    }

    // Primitive types in switch selectors (Java 25 preview feature)
    private static void switchPatternMatchingPrimitiveSelector() {
        Object[] testValues = { (byte)42, (short)42, (char)42, 42, 42L, 3.14f, 3.14, 1_000_000_000_000L };

        for (Object value : testValues) {
            System.out.print("Value: " + value + " -> ");
            switch (value) {
                case byte b -> System.out.println("instanceof byte:   " + b);
                case short s -> System.out.println("instanceof short:  " + s);
                case char c -> System.out.println("instanceof char:   " + c);
                case int i -> System.out.println("instanceof int:    " + i);
                case long l -> System.out.println("instanceof long:   " + l);
                case float f -> System.out.println("instanceof float:  " + f);
                case double d -> System.out.println("instanceof double: " + d);
                default -> System.out.println("Unknown primitive type");
            }
        }
    }

}
