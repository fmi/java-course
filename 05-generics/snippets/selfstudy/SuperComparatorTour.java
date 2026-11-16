/*
 * LAB 05 · SELF-STUDY · `? super` that is NOT about put. (not walked through)
 * List.sort has signature: sort(Comparator<? super E>). So a List<ElectricCar>
 * can be sorted by a comparator written for ANY supertype (Car, Machine) —
 * an ElectricCar IS-A Car IS-A Machine, so it can be judged by their standards.
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SuperComparatorTour {

    static class Machine {
        final String model; final int power;

        Machine(String model, int power) {
            this.model = model; this.power = power;
        }

        public String toString() {
            return model;
        }
    }

    static class Car extends Machine {
        final int seats;

        Car(String model, int power, int seats) {
            super(model, power); this.seats = seats;
        }
    }

    static class ElectricCar extends Car {
        final int batteryKwh;
        
        ElectricCar(String model, int power, int seats, int batteryKwh) {
            super(model, power, seats); this.batteryKwh = batteryKwh;
        }
    }

    // comparators written at different levels of the hierarchy
    static final Comparator<Machine>     BY_POWER   = new ByPower();
    static final Comparator<Car>         BY_SEATS   = new BySeats();
    static final Comparator<ElectricCar> BY_BATTERY = new ByBattery();

    static class ByPower implements Comparator<Machine> {
        public int compare(Machine a, Machine b) {
            return Integer.compare(a.power, b.power);
        }
    }

    static class BySeats implements Comparator<Car> {
        public int compare(Car a, Car b) {
            return Integer.compare(a.seats, b.seats);
        }
    }

    static class ByBattery implements Comparator<ElectricCar> {
        public int compare(ElectricCar a, ElectricCar b) {
            return Integer.compare(a.batteryKwh, b.batteryKwh);
        }
    }

    public static void main(String... args) {
        List<ElectricCar> cars = new ArrayList<>(List.of(
            new ElectricCar("Tesla", 670, 5, 100),
            new ElectricCar("Leaf",  150, 5, 40),
            new ElectricCar("Taycan",750, 4, 93)
        ));

        // sort(Comparator<? super ElectricCar>) accepts a comparator of E or ANY supertype:
        cars.sort(BY_POWER);        // Comparator<Machine>     — a supertype
        System.out.println(cars);   // [Leaf, Tesla, Taycan]   (power 150 < 670 < 750)

        cars.sort(BY_SEATS);        // Comparator<Car>         — a supertype
        System.out.println(cars);   // [Taycan, Leaf, Tesla]   (Taycan=4 seats first; rest=5, stable order)

        cars.sort(BY_BATTERY);      // Comparator<ElectricCar> — the exact type
        System.out.println(cars);   // [Leaf, Taycan, Tesla]   (battery 40 < 93 < 100)

        // Why `? super` and not just Comparator<E>? Because a Comparator<Machine> is perfectly
        // able to compare ElectricCars — it just uses the Machine-level fields. The `super`
        // wildcard lets us reuse general-purpose comparators on more specific lists.
    }
}
