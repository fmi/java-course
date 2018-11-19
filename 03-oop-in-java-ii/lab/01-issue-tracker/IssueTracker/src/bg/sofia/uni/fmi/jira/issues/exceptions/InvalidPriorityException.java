package bg.sofia.uni.fmi.jira.issues.exceptions;

public class InvalidPriorityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidPriorityException(String message) {
		super(message);
	}
}
