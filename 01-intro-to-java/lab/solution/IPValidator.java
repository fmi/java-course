public class IPValidator {

    public static boolean validateIPv4Address(String str) {
        String[] tokens = str.split("\\."); // The '.' (dot) is a special character in regex: it matches any single character except newlines. Therefore, we need to escape it with '\'. But '\' is also an escape character in Java literal strings, therefore two backslashes must be used.
        if (tokens.length != 4) {
            return false;
        }
        for (String token : tokens) {
            if (!isValidIPv4Token(token)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidIPv4Token(String token) {
        if (token.isEmpty()) {
            return false;
        }
        if (token.startsWith("0") && token.length() > 1) {
            return false;
        }

        int octet = 0;

        for (char digit : token.toCharArray()) {
            if (digit < '0' || digit > '9') {
                return false;
            }
            octet = octet * 10 + (digit - '0');
        }

        return octet < 256;
    }

}
