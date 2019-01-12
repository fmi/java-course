package bg.sofia.uni.fmi.mjt.christmas;

import java.util.LinkedList;
import java.util.Queue;

public class Workshop {

	private static final int NUMBER_OF_ELVES = 20;

	private volatile boolean isChristmasTime = false;

	private int wishCounter = 0;

	private Queue<Gift> elvesBacklog = new LinkedList<>();

	private Elf[] elves;

	public Workshop() {
		startElves();
	}

	private void startElves() {
		this.elves = new Elf[NUMBER_OF_ELVES];

		for (int i = 0; i < NUMBER_OF_ELVES; i++) {
			elves[i] = new Elf(i, this);
			elves[i].start();
		}
	}

	public synchronized void postWish(Gift gift) {
		elvesBacklog.add(gift);
		wishCounter++;

		this.notify();
	}

	public synchronized Gift nextGift() {
		while (!isChristmasTime && elvesBacklog.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return (elvesBacklog.isEmpty()) ? null : elvesBacklog.poll();
	}

	public synchronized int getWishCount() {
		return wishCounter;
	}

	public Elf[] getElves() {
		return elves;
	}

	public void setChristmasTime() {
		this.isChristmasTime = true;
	}

}
