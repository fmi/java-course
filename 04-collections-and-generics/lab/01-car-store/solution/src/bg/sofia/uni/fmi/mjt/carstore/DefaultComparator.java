package bg.sofia.uni.fmi.mjt.carstore;

import java.util.Comparator;

import bg.sofia.uni.fmi.mjt.carstore.car.Car;

public class DefaultComparator implements Comparator<Car> {

	@Override
	public int compare(Car one, Car two) {
		int byModel = one.getModel().compareTo(two.getModel());
		if (byModel == 0) {
			return Integer.compare(one.getYear(), two.getYear());
		}

		return byModel;
	}
}
