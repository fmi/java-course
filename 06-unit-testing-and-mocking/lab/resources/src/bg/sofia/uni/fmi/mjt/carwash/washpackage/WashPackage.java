package bg.sofia.uni.fmi.mjt.carwash.washpackage;

import bg.sofia.uni.fmi.mjt.carwash.exception.UnsupportedVehicleException;
import bg.sofia.uni.fmi.mjt.carwash.plan.WashPlan;
import bg.sofia.uni.fmi.mjt.carwash.vehicle.Vehicle;

public interface WashPackage {

    /**
     * Creates the wash plan for the given vehicle.
     * This operation has no side effects.
     *
     * @param vehicle the vehicle to wash
     * @return a non-null, immutable wash plan
     * @throws IllegalArgumentException if vehicle is null
     * @throws UnsupportedVehicleException if this package does not support the vehicle
     */
    WashPlan createPlan(Vehicle vehicle);

}
