package bg.sofia.uni.fmi.mjt.restaurant;

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class MJTDiningPlace implements Restaurant {

	private static final int DEFAULT_CHEFS_COUNT = 20;

	private final Queue<Order> orders;
	private final int numberOfChefs;
	private final Chef[] chefs;
	private final AtomicInteger ordersCount;

	private boolean isClosingTime;

	public MJTDiningPlace() {
		this(DEFAULT_CHEFS_COUNT);
	}

	public MJTDiningPlace(int numberOfChefs) {
		this.orders = new PriorityQueue<>(Collections.reverseOrder());
		this.numberOfChefs = numberOfChefs;
		this.chefs = new Chef[numberOfChefs];
		this.ordersCount = new AtomicInteger();
		initChefs();
	}

	private void initChefs() {
		for (int i = 0; i < numberOfChefs; i++) {
			chefs[i] = new Chef(i, this);
			chefs[i].setName("Chef " + i);
			chefs[i].start();
		}
	}

	@Override
	public void submitOrder(Order order) {
		ordersCount.incrementAndGet();

		synchronized (this) {
			orders.add(order);
			this.notifyAll();
		}
	}

	@Override
	public synchronized Order nextOrder() {
		while (orders.isEmpty() && !isClosingTime) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return orders.isEmpty() ? null : orders.poll();
	}

	@Override
	public int getOrdersCount() {
		return ordersCount.get();
	}

	@Override
	public Chef[] getChefs() {
		return chefs;
	}

	@Override
	public synchronized void close() {
		isClosingTime = true;
		this.notifyAll();
	}

}