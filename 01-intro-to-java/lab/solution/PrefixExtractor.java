public class PrefixExtractor {

    public static String getLongestCommonPrefix(String[] words) {
        if (words == null || words.length == 0 || words[0].isEmpty()) {
            return "";
        }

        if (words.length == 1) {
            return words[0];
        }

        StringBuilder prefix = new StringBuilder();

        for (int checkIndex = 0; checkIndex < words[0].length(); checkIndex++) {
            char commonChar = words[0].charAt(checkIndex);
            for (int i = 1; i < words.length; i++) {
                if (words[i] == null || words[i].isEmpty() || words[i].length() == checkIndex ||
                    words[i].charAt(checkIndex) != commonChar) {
                    return prefix.toString();
                }
            }

            prefix.append(commonChar);
        }

        return prefix.toString();
    }

    public static void main(String[] args) {
        System.out.println(getLongestCommonPrefix(new String[] {"flower", "flow", "flight"})); // "fl"
        System.out.println(getLongestCommonPrefix(new String[] {"dog", "racecar", "car"})); // ""
        System.out.println(getLongestCommonPrefix(new String[] {"cat"})); // "cat"
        System.out.println(getLongestCommonPrefix(new String[] {"protein", "protest", "protozoa"})); // "prot"
    }

}
