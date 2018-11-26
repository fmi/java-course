package bg.sofia.uni.fmi.jira.issues.exceptions;

public class InvalidDescriptionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidDescriptionException(String message) {
		super(message);
	}
}