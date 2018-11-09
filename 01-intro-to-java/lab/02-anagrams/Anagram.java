public class Anagram {

	public boolean isAnagram(String input) {
		int[] letters = new int[26]; // will be initialized with zeros
		String[] words = input.toLowerCase().split(" ");

		for (char c : words[0].toCharArray()) {
			if (c >= 'a' && c <= 'z') {
				letters[c - 'a']++;
			}
		}

		for (char c : words[1].toCharArray()) {
			if (c >= 'a' && c <= 'z') {
				letters[c - 'a']--;
			}
		}

		for (int i : letters) {
			if (i != 0) {
				return false;
			}
		}

		return true;
	}
}
