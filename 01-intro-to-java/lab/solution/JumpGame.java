public class JumpGame {

    public static boolean canWin(int[] array) {
        int reachable = 0;
        for (int i = 0; i < array.length; i++) {
            if (i > reachable) {
                return false;
            }
            reachable = Math.max(reachable, i + array[i]);
        }
        return true;
    }

}
