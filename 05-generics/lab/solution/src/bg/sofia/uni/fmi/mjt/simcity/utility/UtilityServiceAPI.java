package bg.sofia.uni.fmi.mjt.simcity.utility;

import bg.sofia.uni.fmi.mjt.simcity.property.billable.Billable;

import java.util.Map;

public interface UtilityServiceAPI {

    /**
     * Retrieves the costs of a specific utility for a given billable building.
     *
     * @param utilityType The utility type used for the costs' calculation.
     * @param billable    The billable building for which the utility costs will be calculated.
     * @return The cost of the specified utility for the billable building.
     * @throws IllegalArgumentException if the utility or billable is null.
     */
    <T extends Billable> double getUtilityCosts(UtilityType utilityType, T billable);

    /**
     * Calculates the total utility costs for a given billable building.
     *
     * @param billable The billable building for which total utility costs are calculated.
     * @return The total cost of all utilities for the billable building.
     * @throws IllegalArgumentException if the billable is null.
     */
    <T extends Billable> double getTotalUtilityCosts(T billable);

    /**
     * Computes the absolute difference in utility costs between two billable buildings for each utility type.
     *
     * @param firstBillable  The first billable building used for the cost comparison.
     * @param secondBillable The second billable building used for the cost comparison.
     * @return An unmodifiable map containing the absolute difference in costs between the buildings for each utility.
     * @throws IllegalArgumentException if any billable is null.
     */
    <T extends Billable> Map<UtilityType, Double> computeCostsDifference(T firstBillable, T secondBillable);

}
