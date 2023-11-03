package comparators;

import java.util.Comparator;

public class CarByTopSpeedDescComparator implements Comparator<Car> {

    @Override
    public int compare(Car first, Car second) {
        // Let's sort the cars by top speed in descending order.
        // Returns a negative integer, zero or a positive integer when the first car's top speed
        // is greater than, equal to, or less than the top speed of the specified object.
        return Integer.compare(second.getTopSpeed(), first.getTopSpeed());
    }

}
