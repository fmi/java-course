import java.util.Arrays;

public class SwitchExamples {

	public static void main(String[] args) {
		String foodForToday = "RAMEN";
		System.out.println(oldSwitchFoodGrader(foodForToday));
		System.out.println(java15FoodGrader(foodForToday));

		System.out.println(switchPatternMatchingStringConvert("Hello"));
		System.out.println(switchPatternMatchingStringConvert(25));
		System.out.println(switchPatternMatchingStringConvert(new char[] { 'a', 'b', 'c' }));
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

	// Preview feature since JDK 17, finalized with JDK 21
	private static String switchPatternMatchingStringConvert(Object inputObject) {
		return switch (inputObject) {
		case char[] charArray -> Arrays.toString(charArray);
		case Integer i -> "" + i;
		case String _ -> "Are you really trying to convert a string to a string?"; // Unnamed variable - we won't use it
																					// but instead scold the programmer.
																					// Note: Also a preview-feature in JDK 21
		case null -> "It's a null reference"; // able to handle null references
        default -> "It is none of the known data types";
		};
	}
}
