package bg.sofia.uni.fmi.mjt.shopping;

public class ItemNotFoundException extends Exception {

	private static final long serialVersionUID = 3259719714734185553L;

	public ItemNotFoundException() {
		super("Item does not exist");
	}

}
