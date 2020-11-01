public class Remembrall {

    // O(N) algorithm suggested by Vladimir Karachomakov

    public static boolean isPhoneNumberForgettable(String phoneNumber) {

        if (phoneNumber == null || phoneNumber.isBlank()) {
            return false;
        }

        char[] arr = phoneNumber.toCharArray();
        boolean[] visited = new boolean[10];

        for (char c : arr) {
            if (c == '-' || c == ' ') {
                continue;
            }
            int digit = c - '0';
            if (digit >= 0 && digit < 10) {
                if (visited[digit]) {
                    return false;
                }
                visited[digit] = true;
            } else {
                return true;
            }
        }

        return true;
    }

}
