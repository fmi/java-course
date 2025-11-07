import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

public class Comparables {
    public static void main(String[] args) {
        List<ElectricCar> cars = new ArrayList<>(List.of(
                new ElectricCar("Tesla Model S", 670, 5, 100),
                new ElectricCar("Nissan Leaf", 150, 5, 40),
                new ElectricCar("Porsche Taycan", 750, 4, 93)
        ));

        // Comparator defined for Machine
        Comparator<Machine> machineByPowerComparator = Comparator.comparingInt(m -> m.power);

        // Comparator defined for Car
        Comparator<Car> carByNumberOfSeatsComparator = Comparator.comparingInt(m -> m.seats);

        // Comparator defined for ElectricCar
        Comparator<ElectricCar> elCarByBatteryCapacityComparator = Comparator.comparingInt(m -> m.batteryCapacity);

        // sort(Comparator<? super ElectricCar>)
        // hence it accepts:
        // sort(Comparator<Machine>)
        cars.sort(machineByPowerComparator);
        cars.forEach(System.out::println);

        // sort(Comparator<Car>)
        cars.sort(carByNumberOfSeatsComparator);
        cars.forEach(System.out::println);

        // sort(Comparator<ElectricCar>)
        cars.sort(elCarByBatteryCapacityComparator);
        cars.forEach(System.out::println);
    }
}
