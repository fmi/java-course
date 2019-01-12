package bg.sofia.uni.fmi.mjt.christmas;

import java.util.Random;

public class Kid implements Runnable {

	private static final int MAX_TIME = 3000;
	private static Random random = new Random();

	private Workshop workshop;

	public Kid(Workshop workshop) {
		this.workshop = workshop;
	}

	private void makeWish() {
		Gift gift = Gift.getGift();
		workshop.postWish(gift);
	}

	@Override
	public void run() {
		try {
			Thread.sleep(random.nextInt(MAX_TIME));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		makeWish();
	}
}
