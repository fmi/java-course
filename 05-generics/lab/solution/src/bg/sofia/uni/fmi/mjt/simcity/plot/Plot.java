package bg.sofia.uni.fmi.mjt.simcity.plot;

import bg.sofia.uni.fmi.mjt.simcity.Assert;
import bg.sofia.uni.fmi.mjt.simcity.exception.BuildableAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.simcity.exception.BuildableNotFoundException;
import bg.sofia.uni.fmi.mjt.simcity.exception.InsufficientPlotAreaException;
import bg.sofia.uni.fmi.mjt.simcity.property.buildable.Buildable;

import java.util.HashMap;
import java.util.Map;

public class Plot<E extends Buildable> implements PlotAPI<E> {

    private final int initialBuildableArea;

    private int remainingBuildableArea;
    private Map<String, E> buildings;

    public Plot(int buildableArea) {
        this.initialBuildableArea = buildableArea;
        this.remainingBuildableArea = buildableArea;
        this.buildings = new HashMap<>();
    }

    @Override
    public void construct(String address, E buildable) {
        Assert.notNullOrBlank(address);
        Assert.notNull(buildable);

        if (buildings.containsKey(address)) {
            throw new BuildableAlreadyExistsException("A building with such address already exists on the plot.");
        }

        int requiredBuildableArea = buildable.getArea();
        if (requiredBuildableArea > getRemainingBuildableArea()) {
            throw new InsufficientPlotAreaException("The required buildable area exceeds the remaining plot area.");
        }

        buildings.put(address, buildable);
        remainingBuildableArea -= requiredBuildableArea;
    }

    @Override
    public void constructAll(Map<String, E> buildables) {
        Assert.notNullOrEmpty(buildables);

        int lastRemainingBuildableAreaState = remainingBuildableArea;
        Map<String, E> lastBuildingsState = new HashMap<>(buildings); // shallow copy

        for (Map.Entry<String, E> entry : buildables.entrySet()) {
            try {
                String address = entry.getKey();
                E buildable = entry.getValue();

                construct(address, buildable);
            } catch (Exception e) {
                this.remainingBuildableArea = lastRemainingBuildableAreaState;
                this.buildings = lastBuildingsState;
                throw e;
            }
        }
    }

    @Override
    public void demolish(String address) {
        Assert.notNullOrBlank(address);

        if (!buildings.containsKey(address)) {
            throw new BuildableNotFoundException("Buildable with such address does not exist on the plot.");
        }

        Buildable demolishedBuilding = buildings.remove(address);
        int buildableArea = demolishedBuilding.getArea();
        remainingBuildableArea += buildableArea;
    }

    @Override
    public void demolishAll() {
        remainingBuildableArea = initialBuildableArea;
        buildings.clear();
    }

    @Override
    public Map<String, E> getAllBuildables() {
        return Map.copyOf(buildings);
    }

    @Override
    public int getRemainingBuildableArea() {
        return remainingBuildableArea;
    }

}
