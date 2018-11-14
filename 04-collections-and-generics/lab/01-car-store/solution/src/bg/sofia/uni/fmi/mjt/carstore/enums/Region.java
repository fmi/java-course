package bg.sofia.uni.fmi.mjt.carstore.enums;

import java.util.Random;

public enum Region {

	SOFIA("CB"), BURGAS("A"), VARNA("B"), PLOVDIV("PB"), RUSE("P"), GABROVO("EB"), VIDIN("BH"), VRATSA("BP");

	private String prefix;
	private int currentNumber = 1000;

	private Region(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getRegistrationNumber() {
		int current = currentNumber;
		currentNumber++;

		return String.format("%s%d%c%c", prefix, current, getRandomChar(), getRandomChar());
	}

	private char getRandomChar() {
		Random random = new Random();
		return (char) (random.nextInt(26) + 'A');
	}
}
