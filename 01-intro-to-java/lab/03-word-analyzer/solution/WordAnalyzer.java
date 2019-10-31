public class WordAnalyzer {

    static final int MAX_CHAR = 26;

    public static String getSharedLetters(String word1, String word2) {

        // two arrays of length 26 to store occurrence
        // of a letters alphabetically for each string
        boolean[] letters1 = new boolean[MAX_CHAR];
        boolean[] letters2 = new boolean[MAX_CHAR];

        word1 = word1.toLowerCase();
        word2 = word2.toLowerCase();

        for (int i = 0 ; i < word1.length() ; i++) {
            letters1[word1.charAt(i) - 'a'] = true;
        }

        for (int i = 0 ; i < word2.length() ; i++)
            letters2[word2.charAt(i) - 'a'] = true;

        String sharedLetters = "";

        for (int i = 0 ; i < MAX_CHAR ; i++) {
            if (letters1[i] && letters2[i]) {
                sharedLetters += (char)('a' + i);
            }
        }

        return sharedLetters;
    }

    public static void main(String[] args) {
        System.out.println(getSharedLetters("house", "home")); // "eho"
        System.out.println(getSharedLetters("Micky", "mouse")); // "m"
        System.out.println(getSharedLetters("house", "villa")); // ""
    }
}
