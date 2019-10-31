public class FunnelChecker {

    public static boolean isFunnel(String str1, String str2) {
        for (int i = 0; i < str1.length(); i++) {
            if ((str1.substring(0, i) + str1.substring(i + 1)).equals(str2)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(isFunnel("leave", "eave")); // true
        System.out.println(isFunnel("reset", "rest")); // true
        System.out.println(isFunnel("dragoon", "dragon")); // true
        System.out.println(isFunnel("eave", "leave")); // false
        System.out.println(isFunnel("sleet", "lets")); // false
        System.out.println(isFunnel("skiff", "ski")); // false
    }

}
