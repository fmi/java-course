package bg.sofia.uni.fmi.mjt.carwash.controller;

import bg.sofia.uni.fmi.mjt.carwash.WashRequest;
import bg.sofia.uni.fmi.mjt.carwash.exception.WashUnavailableException;
import bg.sofia.uni.fmi.mjt.carwash.plan.WashStep;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class InMemoryWashController implements WashController {

    private static final int WATER = 0;
    private static final int SHAMPOO = 1;
    private static final int WAX = 2;

    private List<Integer> supplies;
    private boolean operational = true;

    private final List<WashRequest> acceptedWashes = new ArrayList<>();

    public InMemoryWashController(WashSupplies initialSupplies) {
        if (initialSupplies == null) {
            throw new IllegalArgumentException("Initial supplies must not be null");
        }

        supplies = new ArrayList<>(List.of(
            initialSupplies.waterLitres(), initialSupplies.shampooMl(), initialSupplies.waxMl()));
    }

    @Override
    public boolean submit(WashRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request must not be null");
        }

        if (!operational) {
            throw new WashUnavailableException("Controller is not operational");
        }

        List<Integer> tmp = supplies;

        for (WashStep step : EnumSet.copyOf(request.plan().steps())) {
            if (supplies.get(WATER) < step.getWaterLitres() || supplies.get(SHAMPOO) < step.getShampooMl()
                || supplies.get(WAX) < step.getWaxMl()) {
                supplies = tmp;
                return false;
            }

            supplies.set(WATER, supplies.get(WATER) - step.getWaterLitres());
            supplies.set(SHAMPOO, supplies.get(SHAMPOO) - step.getShampooMl());
            supplies.set(WAX, supplies.get(WAX) - step.getWaxMl());
        }

        acceptedWashes.add(request);
        return true;
    }

    public void refill(WashSupplies additionalSupplies) {
        if (additionalSupplies == null) {
            throw new IllegalArgumentException("Additional supplies must not be null");
        }

        supplies.set(WATER, supplies.get(WATER) + additionalSupplies.waterLitres());
        supplies.set(SHAMPOO, supplies.get(SHAMPOO) + additionalSupplies.shampooMl());
        supplies.set(WAX, supplies.get(WAX) + additionalSupplies.waxMl());
    }

    public void setOperational(boolean operational) {
        this.operational = operational;
    }

    public WashSupplies getSupplies() {
        return new WashSupplies(supplies.get(WATER), supplies.get(SHAMPOO), supplies.get(WAX));
    }

    public List<WashRequest> getAcceptedWashes() {
        return List.copyOf(acceptedWashes);
    }

}
