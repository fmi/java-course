public class Counter {

	public int countTriples(String str) {
		int length = str.length();
		int count = 0;
		for (int i = 0; i < length - 2; i++) {
			if (str.charAt(i) == str.charAt(i + 1)
				&& str.charAt(i + 1) == str.charAt(i + 2)) {
				count++;
			}
		}

		return count;
	}
}
