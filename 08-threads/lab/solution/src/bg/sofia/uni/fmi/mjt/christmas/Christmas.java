package bg.sofia.uni.fmi.mjt.christmas;

public class Christmas {

	private Workshop workshop;
	private int numberOfKids;
	private int christmasTime;

	public Christmas(Workshop workshop, int numberOfKids, int christmasTime) {
		this.workshop = workshop;
		this.numberOfKids = numberOfKids;
		this.christmasTime = christmasTime;
	}

	public static void main(String[] args) {
		int numberOfKids = 1000;
		int christmasTime = 2000;
		
		Christmas christmas = new Christmas(new Workshop(), numberOfKids, christmasTime);
		christmas.celebrate();
	}

	public void celebrate() {
		// Start kids
		Thread[] kids = new Thread[numberOfKids];
		for (int i = 0; i < kids.length; i++) {
			kids[i] = new Thread(new Kid(workshop));
			kids[i].start();
		}

		// Wait for Christmas
		try {
			Thread.sleep(christmasTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Notify that Christmas is here
		synchronized (workshop) {
			workshop.setChristmasTime();
		}

		// Wait for the kids threads to finish
		for (int i = 0; i < kids.length; i++) {
			try {
				kids[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public Workshop getWorkshop() {
		return workshop;
	}

}
