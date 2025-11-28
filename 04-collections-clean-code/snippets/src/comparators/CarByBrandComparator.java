package comparators;

import java.util.Comparator;

/**
 * Comparator that sorts cars alphabetically by brand name.
 */
public class CarByBrandComparator implements Comparator<Car> {

    @Override
    public int compare(Car a, Car b) {
        return a.getBrand().compareTo(b.getBrand());
    }

}
