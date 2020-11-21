package bg.sofia.uni.fmi.mjt.testing.math;

public class MathUtils {

    public static boolean isPrime(int n) throws IllegalArgumentException {

        if (n < 2) {
            throw new IllegalArgumentException("Number not in the domain of function");
        }

        if (n == 2) {
            return true;
        }

        if (n % 2 == 0) {
            return false;
        }

        for (int i = 3; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;

    }

}
