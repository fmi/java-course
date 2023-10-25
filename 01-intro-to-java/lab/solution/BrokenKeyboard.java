public class BrokenKeyboard {

    public static int calculateFullyTypedWords(String text, String brokenLetters) {
        String[] allWords = text.split(" ");
        int corruptedWordsCount = 0;

        for (String word : allWords) {
            if (word.isBlank()) {
                corruptedWordsCount++;
                continue;
            }

            for (char c : brokenLetters.toCharArray()) {
                if (word.contains(Character.toString(c))) {
                    corruptedWordsCount++;
                    break;
                }
            }
        }

        return allWords.length - corruptedWordsCount;
    }
}
