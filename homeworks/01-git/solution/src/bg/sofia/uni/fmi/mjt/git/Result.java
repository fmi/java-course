package bg.sofia.uni.fmi.mjt.git;

public class Result {

	private String message;
	private boolean isSuccessful;

	public static Result success(String message) {
		return new Result(message, true);
	}

	public static Result fail(String message) {
		return new Result(message, false);
	}

	private Result(String message, boolean isSuccessful) {
		this.message = message;
		this.isSuccessful = isSuccessful;
	}

	public String getMessage() {
		return message;
	}

	public boolean isSuccessful() {
		return isSuccessful;
	}
}
