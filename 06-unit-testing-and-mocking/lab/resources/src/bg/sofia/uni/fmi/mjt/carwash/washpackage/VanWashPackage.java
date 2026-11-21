package bg.sofia.uni.fmi.mjt.carwash.washpackage;

import bg.sofia.uni.fmi.mjt.carwash.exception.UnsupportedVehicleException;
import bg.sofia.uni.fmi.mjt.carwash.plan.WashPlan;
import bg.sofia.uni.fmi.mjt.carwash.plan.WashStep;
import bg.sofia.uni.fmi.mjt.carwash.vehicle.Vehicle;
import bg.sofia.uni.fmi.mjt.carwash.vehicle.VehicleType;

import java.util.ArrayList;
import java.util.List;

public class VanWashPackage implements WashPackage {

    private static final int PRICE_CENTS = 1500;

    private final List<WashStep> steps = new ArrayList<>();

    @Override
    public WashPlan createPlan(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle must not be null");
        }

        if (vehicle.type() != VehicleType.VAN) {
            throw new UnsupportedVehicleException("The van package supports vans only");
        }

        steps.addAll(List.of(WashStep.PREWASH, WashStep.SHAMPOO, WashStep.RINSE, WashStep.DRY));

        return new WashPlan("Van", PRICE_CENTS, steps);
    }

}
