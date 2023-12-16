package bg.sofia.uni.fmi.mjt.simcity.property.buildable;

public interface Buildable {

    /**
     * Retrieves the type of the building.
     *
     * @return The specific type of the building, represented by a BuildableType.
     * Examples include Residential, Commercial, Industrial, etc.
     */
    BuildableType getType();

    /**
     * Retrieves the total area of the building.
     *
     * @return The total area of the building in square metric units.
     */
    int getArea();

}
