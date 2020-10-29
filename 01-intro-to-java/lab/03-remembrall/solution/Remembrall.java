import java.util.Arrays;

public class Remembrall {

    public static boolean isPhoneNumberForgettable(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            return false;
        }

        phoneNumber = phoneNumber.replace("-", "").replace(" ", "");

        char[] chars = phoneNumber.toCharArray();
        Arrays.sort(chars);
        for (int i = chars.length - 1; i >= 1; i--) {
            if (Character.isLetter(chars[i])) {
                return true;
            }

            if (chars[i] == chars[i - 1]) {
                return false;
            }
        }

        return true;
    }

}
