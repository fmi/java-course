package bg.sofia.uni.fmi.mjt.carwash;

import bg.sofia.uni.fmi.mjt.carwash.plan.WashPlan;
import bg.sofia.uni.fmi.mjt.carwash.vehicle.Vehicle;

public record WashRequest(Vehicle vehicle, WashPlan plan) {

    public WashRequest {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle must not be null");
        }

        if (plan == null) {
            throw new IllegalArgumentException("Wash plan must not be null");
        }
    }

}
