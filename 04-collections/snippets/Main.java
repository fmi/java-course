import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Main {

    public static Comparator<Car> carNameComparator = new Comparator<Car>() {
        @Override
        public int compare(Car first, Car second) {
            return first.getBrand().compareTo(second.getBrand());
        }
    };

    public static Comparator<Car> carTopSpeedDecComparator = new Comparator<Car>() {
        @Override
        public int compare(Car first, Car second) {
            // Let's sort the cars by top speed in descending order.
            // Returns a positive integer, zero or a negative integer when this car's top speed
            // is greater than, equal to, or less than the top speed of the specified object.
            return second.getTopSpeed() - first.getTopSpeed();
        }
    };

    public static void main(String[] args) {
        Car first = new Car(200, "red", "Ferrari");
        Car second = new Car(100, "green", "Mercedes");
        Car third = new Car(50, "blue", "BMW");
        Car fourth = new Car(150, "black", "Audi");

        // Lists
        List<Car> cars = new ArrayList<>();

        cars.add(first);
        cars.add(second);
        cars.add(third);
        cars.add(fourth);

        Collections.sort(cars);
        System.out.println("Default sorting of car list by topSpeed in ascedning order:");
        for (Car car : cars) {
            System.out.println(car);
        }

        Collections.sort(cars, carNameComparator);
        System.out.println("\nCustom sorting of car list by name lexicographically:");
        for (Car car : cars) {
            System.out.println(car);
        }

        Collections.sort(cars, carTopSpeedDecComparator);
        System.out.println("\nCustom sorting of car list by topSpeed in descending order:");
        for (Car car : cars) {
            System.out.println(car);
        }

        // Map
        Map<Car, Integer> carStorage = new HashMap<>();
        carStorage.put(first, 3);
        carStorage.put(second, 4);
        carStorage.put(third, 10);
        carStorage.put(fourth, 25);

        System.out.println("\nNatural ordering of carStorage:");
        for (Map.Entry<Car, Integer> entry : carStorage.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        System.out.println("\nDefault sorting of sortedCarStorage map by topSpeed in ascedning order:");
        Map<Car, Integer> sortedStorage = new TreeMap<>();
        sortedStorage.putAll(carStorage);
        for (Map.Entry<Car, Integer> entry : sortedStorage.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        System.out.println("\nCustom sorting of sortedCarStorageCustom map by name lexicographically:");
        Map<Car, Integer> sortedStorageCustom = new TreeMap<>(carNameComparator);
        sortedStorageCustom.putAll(carStorage);
        for (Map.Entry<Car, Integer> entry : sortedStorageCustom.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
    
}
