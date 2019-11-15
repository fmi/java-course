package bg.sofia.uni.fmi.mjt.shopping.item;

public class Apple implements Item {

	private String name = "";
	private String description = "";
	private double price = 0;

	public Apple(String name, String desc, double price){        
        this.name = name;
        this.description = desc;
        this.price = price;
    }
	
    @Override
    public String getName() {
        return this.name;
    }

    @Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public double getPrice() {
		return this.price;
	}

}
