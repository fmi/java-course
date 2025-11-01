package comparators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Main {

    public static void main(String... args) {

        Car first = new Car("1G1RB5F49KP123456", 200, "red", "Ferrari");
        Car second = new Car("JN1AZ42MX987654", 100, "green", "Mercedes");
        Car third = new Car("2HKM28NG543210", 50, "blue", "BMW");
        Car fourth = new Car("WBA345F83PT567890", 150, "black", "Audi");

        List<Car> cars = new ArrayList<>();

        cars.add(first);
        cars.add(second);
        cars.add(third);
        cars.add(fourth);

        Collections.sort(cars);
        System.out.println("Sorting the cars according to their natural order: by topSpeed, ascending:");
        for (Car car : cars) {
            System.out.println(car);
        }

        Collections.sort(cars, new CarByBrandComparator());
        System.out.println("\nCustom sorting the cars by brand name lexicographically:");
        for (Car car : cars) {
            System.out.println(car);
        }

        Collections.sort(cars, new CarByTopSpeedDescComparator());
        System.out.println("\nCustom sorting the cars by topSpeed in descending order:");
        for (Car car : cars) {
            System.out.println(car);
        }

        // Map
        Map<Car, Integer> carStorage = new HashMap<>();
        carStorage.put(first, 3);
        carStorage.put(second, 4);
        carStorage.put(third, 10);
        carStorage.put(fourth, 25);

        System.out.println("\nIterating the carStorage hash map:");
        for (Map.Entry<Car, Integer> entry : carStorage.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        System.out.println("\nSorting the cars storage map by the key's natural order: topSpeed, ascending:");
        Map<Car, Integer> sortedStorage = new TreeMap<>();
        sortedStorage.putAll(carStorage);
        for (Map.Entry<Car, Integer> entry : sortedStorage.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        System.out.println("\nCustom sorting the cars storage map by the key's by brand name lexicographically:");
        Map<Car, Integer> sortedStorageCustom = new TreeMap<>(new CarByBrandComparator());
        sortedStorageCustom.putAll(carStorage);
        for (Map.Entry<Car, Integer> entry : sortedStorageCustom.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

}
