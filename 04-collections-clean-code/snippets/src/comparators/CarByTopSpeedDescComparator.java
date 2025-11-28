package comparators;

import java.util.Comparator;

/**
 * Comparator that sorts cars by top speed in descending order.
 */
public class CarByTopSpeedDescComparator implements Comparator<Car> {

    @Override
    public int compare(Car a, Car b) {
        return Integer.compare(b.getTopSpeed(), a.getTopSpeed());
    }

}
