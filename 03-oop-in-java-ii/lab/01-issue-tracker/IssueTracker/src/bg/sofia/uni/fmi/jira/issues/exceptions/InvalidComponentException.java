package bg.sofia.uni.fmi.jira.issues.exceptions;

public class InvalidComponentException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidComponentException(String message) {
		super(message);
	}
}
