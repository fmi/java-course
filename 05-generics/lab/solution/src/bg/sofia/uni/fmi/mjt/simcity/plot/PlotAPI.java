package bg.sofia.uni.fmi.mjt.simcity.plot;

import bg.sofia.uni.fmi.mjt.simcity.exception.BuildableAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.simcity.exception.BuildableNotFoundException;
import bg.sofia.uni.fmi.mjt.simcity.exception.InsufficientPlotAreaException;
import bg.sofia.uni.fmi.mjt.simcity.property.buildable.Buildable;

import java.util.Map;

public interface PlotAPI<E extends Buildable> {

    /**
     * Constructs a buildable on the plot.
     *
     * @param address   the address where the buildable should be constructed.
     * @param buildable the buildable that should be constructed on the given address.
     * @throws IllegalArgumentException        if the address is null or blank.
     * @throws IllegalArgumentException        if the buildable is null.
     * @throws BuildableAlreadyExistsException if the address is already occupied on the plot.
     * @throws InsufficientPlotAreaException   if the required area exceeds the remaining plot area.
     */
    void construct(String address, E buildable);

    /**
     * Constructs multiple buildables on the plot.
     * This method ensures that either all operations are successfully completed
     * or no changes are made to the plot's state.
     *
     * @param buildables a Map containing the addresses and corresponding buildable entities.
     * @throws IllegalArgumentException        if the map of buildables is null, empty.
     * @throws BuildableAlreadyExistsException if any of the addresses is already occupied on the plot.
     * @throws InsufficientPlotAreaException   if the combined area of the provided buildables exceeds the remaining plot area.
     */
    void constructAll(Map<String, E> buildables);

    /**
     * Demolishes a buildable from the plot.
     *
     * @param address the address of the buildable which should be demolished.
     * @throws IllegalArgumentException   if the provided address is null or blank.
     * @throws BuildableNotFoundException if buildable with such address does not exist on the plot.
     */
    void demolish(String address);

    /**
     * Demolishes all buildables from the plot.
     */
    void demolishAll();

    /**
     * Retrieves all buildables present on the plot.
     *
     * @return An unmodifiable copy of the buildables present on the plot.
     */
    Map<String, E> getAllBuildables();

    /**
     * Retrieves the remaining buildable area on the plot.
     *
     * @return The remaining buildable area on the plot.
     */
    int getRemainingBuildableArea();

}
