package bg.sofia.uni.fmi.mjt.carstore.exception;

public class CarNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1460125818886683558L;

	public CarNotFoundException() {
		// Empty body
	}

	public CarNotFoundException(String message) {
		super(message);
	}
}
