package bg.sofia.uni.fmi.mjt.carwash.washpackage;

import bg.sofia.uni.fmi.mjt.carwash.exception.UnsupportedVehicleException;
import bg.sofia.uni.fmi.mjt.carwash.plan.WashPlan;
import bg.sofia.uni.fmi.mjt.carwash.plan.WashStep;
import bg.sofia.uni.fmi.mjt.carwash.vehicle.Vehicle;
import bg.sofia.uni.fmi.mjt.carwash.vehicle.VehicleType;

import java.util.List;

public class PremiumWashPackage implements WashPackage {

    private static final int PRICE_CENTS = 1000;

    @Override
    public WashPlan createPlan(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle must not be null");
        }

        if (vehicle.type() != VehicleType.CAR) {
            throw new UnsupportedVehicleException("The premium package supports cars only");
        }

        return new WashPlan("Premium", PRICE_CENTS,
            List.of(WashStep.PREWASH, WashStep.SHAMPOO, WashStep.RINSE, WashStep.WAX, WashStep.DRY));
    }

}
