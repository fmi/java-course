package bg.sofia.uni.fmi.mjt.carwash;

import bg.sofia.uni.fmi.mjt.carwash.controller.WashController;
import bg.sofia.uni.fmi.mjt.carwash.exception.UnsupportedVehicleException;
import bg.sofia.uni.fmi.mjt.carwash.exception.WashUnavailableException;
import bg.sofia.uni.fmi.mjt.carwash.plan.WashPlan;
import bg.sofia.uni.fmi.mjt.carwash.vehicle.Vehicle;
import bg.sofia.uni.fmi.mjt.carwash.washpackage.WashPackage;

import java.util.ArrayList;
import java.util.List;

public class CarWashStation {

    private final WashController controller;
    private final ArrayList<WashRequest> acceptedWashes;

    private int creditCents;
    private int lastChargeCents;

    public CarWashStation(int initialCreditCents, WashController controller) {
        if (initialCreditCents < 0) {
            throw new IllegalArgumentException("Initial credit must not be negative");
        }

        if (controller == null) {
            throw new IllegalArgumentException("Controller must not be null");
        }

        this.creditCents = initialCreditCents;
        this.controller = controller;
        this.acceptedWashes = new ArrayList<>();
    }

    public void addCredit(int amountCents) {
        if (amountCents <= 0)
            throw new IllegalArgumentException("Added credit must be positive");

        creditCents += amountCents;
    }

    public boolean requestWash(Vehicle vehicle, WashPackage washPackage) {
        if (vehicle == null || washPackage == null) {
            throw new IllegalArgumentException("Vehicle and wash package must not be null");
        }

        WashPlan p;
        try {
            p = resolvePlan(vehicle, washPackage);
        } catch (UnsupportedVehicleException exception) {
            return false;
        }

        if (!check(p)) {
            return false;
        }

        WashRequest r = new WashRequest(vehicle, p);

        try {
            if (!controller.submit(r)) {
                return false;
            }
        } catch (WashUnavailableException exception) {
            creditCents += lastChargeCents;
            throw exception;
        }

        chargeAndRecord(p, r);
        return true;
    }

    private WashPlan resolvePlan(Vehicle vehicle, WashPackage washPackage) {
        return washPackage.createPlan(vehicle);
    }

    private void chargeAndRecord(WashPlan p, WashRequest r) {
        creditCents -= p.priceCents();
        lastChargeCents = p.priceCents();

        if (!acceptedWashes.contains(r)) {
            acceptedWashes.add(r);
        }
    }

    private boolean check(WashPlan p) {
        if (creditCents >= p.priceCents()) {
            return true;
        } else {
            return false;
        }
    }

    public int getCreditCents() {
        return creditCents;
    }

    public List<WashRequest> getAcceptedWashes() {
        return List.copyOf(acceptedWashes);
    }

}
