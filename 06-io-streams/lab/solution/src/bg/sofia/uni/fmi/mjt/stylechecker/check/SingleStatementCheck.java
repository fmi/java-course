package bg.sofia.uni.fmi.mjt.stylechecker.check;

public class SingleStatementCheck extends CodeCheck {

	public SingleStatementCheck(String errorMessage) {
		super(errorMessage);
	}

	@Override
	public boolean checkForError(String line) {
		int lastIndex = -1;
		for (int i = line.length() - 1; i >= 0; i--) {
			if (line.charAt(i) == ';') {
				if (lastIndex != -1 && lastIndex != i + 1) {
					return true;
				}

				lastIndex = i;
			}
		}

		return false;
	}
}
