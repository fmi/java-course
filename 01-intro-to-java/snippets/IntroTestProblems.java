package bg.uni.sofia.fmi.mjt;

import java.lang.Math;

public class IntroTestProblems {

    public static int digitDistance(int num1, int num2) {
        int sum=0;
        if (num1 < 10) {
            return Math.abs(num1 - num2);
        }
        while(num1>0){
            sum += Math.abs(num1%10 - num2%10);
            num1/=10;
            num2/=10;
        }
        return sum;
    }

    public static int minSwaps(String s1, String s2) {
        int differences = 0;
        for(int i = 0; i < s1.length(); ++i) {
            differences += (s1.charAt(i) == s2.charAt(i)) ? 0 : 1;
        }
        return differences / 2;
    }

    public static int getMissingNo(int[] a, int n)
    {
        int total = n*(n+1)/2; // the sum of all numbers in the [0, n] range
        for (int i = 0; i < n; i++) {
            total -= a[i];
        }
        return total;
    }

    public static double sumIter(int n) {
        double result = 0.0;
        for (int i = 1; i <= n; i++) {
            result += 1.0 / i;
        }
        return result;
    }

    public static double sumRec(int n) {
        if (n == 1) {
            return 1.0;
        } else {
            return 1.0/n + sumRec(n-1);
        }
    }

    public static void main(String[] args) {
        System.out.println(digitDistance(234, 489)); // 12
        System.out.println(minSwaps("1100", "1001")); // 1
        System.out.println(minSwaps("110011", "010111")); // 1
        System.out.println(minSwaps("10011001", "01100110")); // 4
        System.out.println(getMissingNo(new int[]{4, 0, 1, 3}, 4)); // 2
        System.out.println(sumIter(3)); // 1.8333333333333333
        System.out.println(sumRec(3)); // 1.8333333333333333
    }
}
