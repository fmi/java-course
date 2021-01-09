package bg.sofia.uni.fmi.mjt.restaurant;

public interface Restaurant {

	/**
	 * Adds an order to the restaurant's backlog.
	 **/
	public void submitOrder(Order order);

	/**
	 * Returns the next order from the restaurant's backlog that has to be cooked.
	 **/
	public Order nextOrder();

	/**
	 * Returns the total number of orders submitted to the restaurant's.
	 **/
	public int getOrdersCount();

	/**
	 * Returns the restaurant's chefs.
	 **/
	public Chef[] getChefs();

	/**
	 * When this method is called, the restaurant is about to close soon and should not serve more orders.
	 **/
	public void close();

}
