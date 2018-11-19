package bg.sofia.uni.fmi.mjt.cache;

public class CapacityExceededException extends Exception {

	private static final long serialVersionUID = -2476455035877654213L;

	public CapacityExceededException(String string) {
        super(string);
    }
	
}
