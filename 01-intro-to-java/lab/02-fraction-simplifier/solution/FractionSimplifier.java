public class FractionSimplifier {

    private static int gcd(int a, int b) {
        if (a % b == 0) {
            return b;
        }
        return gcd(b, a % b);
    }


    public static String simplify(String fraction) {
        int numerator = Integer.parseInt(fraction.substring(0, fraction.indexOf('/')));
        int denominator = Integer.parseInt(fraction.substring(fraction.indexOf('/') + 1));

        if (numerator % denominator == 0) {
            return String.valueOf(numerator / denominator);
        }

        int temp =  numerator;
        numerator /= gcd(numerator, denominator);
        denominator /= gcd(temp, denominator);

        return numerator + "/" + denominator;
    }

    public static void main(String[] args) {
        System.out.println(simplify("4/6")); // "2/3"
        System.out.println(simplify("10/11")); // "10/11"
        System.out.println(simplify("100/400")); // "1/4"
        System.out.println(simplify("8/4")); // "2"
    }

}
