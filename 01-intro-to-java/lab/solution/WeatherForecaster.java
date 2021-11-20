import java.util.Arrays;

public class WeatherForecaster {

    /* brute force: O(N^2)
       The naive way to solve this problem is to iterate through the array, and for each day,
       iterate through all of the remaining days until you find a warmer temperature.
     */
    public static int[] getsWarmerIn(int[] temperatures) {
        int n = temperatures.length;
        int[] answer = new int[n];
        for (int day = 0; day < n; day++) {
            for (int futureDay = day + 1; futureDay < n; futureDay++) {
                if (temperatures[futureDay] > temperatures[day]) {
                    answer[day] = futureDay - day;
                    break;
                }
            }
        }

        return answer;
    }

    // smart: O(N)
    public static int[] getsWarmerInSmart(int[] temperatures) {
        int[] solution = new int[temperatures.length];
        solution[temperatures.length - 1] = 0;

        for (int i = solution.length - 2; i >= 0; i--) {
            int j = i + 1;
            while (j <= solution.length) {
                if (temperatures[i] < temperatures[j]) {
                    solution[i] = j - i;
                    break;
                } else {
                    if (solution[j] == 0) {
                        break;
                    }
                    j += solution[j];
                }
            }
        }

        return solution;
    }

}
