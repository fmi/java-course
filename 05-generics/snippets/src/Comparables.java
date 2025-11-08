import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// The idea of this snippet is to showcase a real-life usage of
// Comparator<? super E> which is not related to the get/put principle
// the `sort` method of List has the signature `sort(Comparator<? super E> c)`
// The main thing is that you can put a comparator of E or any of E's parents
// because E has inherited all properties of it's parents, hence
// it can be compared by their standards.
// Here we have the hierarchy ElectricCar -> Car -> Machines
// and since an ElectricCar is both a Car and a Machine it can be compared as such.

class Machine {
    final String model;
    final int power;

    Machine(String model, int power) {
        this.model = model;
        this.power = power;
    }
}

class Car extends Machine {
    final int seats;

    Car(String model, int power, int seats) {
        super(model, power);
        this.seats = seats;
    }
}

class ElectricCar extends Car {
    final int batteryCapacity;

    ElectricCar(String model, int power, int seats, int batteryCapacity) {
        super(model, power, seats);
        this.batteryCapacity = batteryCapacity;
    }
}

class MachineByPowerComparator implements Comparator<Machine> {
    @Override
    public int compare(Machine o1, Machine o2) {
        return Integer.compare(o1.power, o2.power);
    }
}

class CarByNumberOfSeatsComparator implements Comparator<Car> {
    @Override
    public int compare(Car o1, Car o2) {
        return Integer.compare(o1.seats, o2.seats);
    }
}

class ElCarByBatteryCapacityComparator implements Comparator<ElectricCar> {
    @Override
    public int compare(ElectricCar o1, ElectricCar o2) {
        return Integer.compare(o1.batteryCapacity, o2.batteryCapacity);
    }
}

public class Comparables {
    public static void main(String[] args) {
        List<ElectricCar> cars = new ArrayList<>(List.of(
                new ElectricCar("Tesla Model S", 670, 5, 100),
                new ElectricCar("Nissan Leaf", 150, 5, 40),
                new ElectricCar("Porsche Taycan", 750, 4, 93)
        ));

        // Comparator defined for Machine
        Comparator<Machine> machineComparator = new MachineByPowerComparator();

        // Comparator defined for Car
        Comparator<Car> carComparator = new CarByNumberOfSeatsComparator();

        // Comparator defined for ElectricCar
        Comparator<ElectricCar> elCarComparator = new ElCarByBatteryCapacityComparator();

        // sort(Comparator<? super ElectricCar>)
        // hence it accepts:
        // sort(Comparator<Machine>)
        cars.sort(machineComparator);
        cars.forEach(System.out::println);

        // sort(Comparator<Car>)
        cars.sort(carComparator);
        cars.forEach(System.out::println);

        // sort(Comparator<ElectricCar>)
        cars.sort(elCarComparator);
        cars.forEach(System.out::println);
    }
}
