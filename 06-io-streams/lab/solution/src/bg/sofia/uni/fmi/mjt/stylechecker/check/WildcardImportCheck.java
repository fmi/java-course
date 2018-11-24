package bg.sofia.uni.fmi.mjt.stylechecker.check;

public class WildcardImportCheck extends CodeCheck {

	public WildcardImportCheck(String errorMessage) {
		super(errorMessage);
	}

	@Override
	public boolean checkForError(String line) {
		return line.trim().split(";")[0].endsWith(".*");
	}
}
