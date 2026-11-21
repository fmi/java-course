package bg.sofia.uni.fmi.mjt.carwash.washpackage;

import bg.sofia.uni.fmi.mjt.carwash.exception.UnsupportedVehicleException;
import bg.sofia.uni.fmi.mjt.carwash.plan.WashPlan;
import bg.sofia.uni.fmi.mjt.carwash.plan.WashStep;
import bg.sofia.uni.fmi.mjt.carwash.vehicle.Vehicle;
import bg.sofia.uni.fmi.mjt.carwash.vehicle.VehicleType;

import java.util.List;

public class BasicWashPackage implements WashPackage {

    @Override
    public WashPlan createPlan(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle must not be null");
        }

        if (vehicle.type() != VehicleType.CAR) {
            throw new UnsupportedVehicleException("The basic package supports cars only");
        }

        return new WashPlan("Basic", 600, List.of(WashStep.SHAMPOO, WashStep.RINSE));
    }

}
