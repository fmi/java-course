public class TourGuide {

    public static int getBestSightseeingPairScore(int[] places) {
        int res = 0, cur = 0;
        for (int p : places) {
            res = Math.max(res, cur + p);
            cur = Math.max(cur, p) - 1;
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(getBestSightseeingPairScore(new int[] {8, 1, 5, 2, 6})); // 11
        System.out.println(getBestSightseeingPairScore(new int[] {1, 2})); // 2
        System.out.println(getBestSightseeingPairScore(new int[] {10, 7, 14})); // 22
        System.out.println(getBestSightseeingPairScore(new int[] {0, 1, 2, 3, 1})); // 4
        System.out.println(getBestSightseeingPairScore(new int[] {4, 4, 4, 4})); // 7
    }

}
