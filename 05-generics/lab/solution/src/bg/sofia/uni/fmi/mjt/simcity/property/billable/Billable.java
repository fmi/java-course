package bg.sofia.uni.fmi.mjt.simcity.property.billable;

import bg.sofia.uni.fmi.mjt.simcity.property.buildable.Buildable;

public interface Billable extends Buildable {

    /**
     * Retrieves the monthly water consumption of the billable building.
     *
     * @return The monthly water consumption of the building in cubic meters (m³).
     */
    double getWaterConsumption();

    /**
     * Retrieves the monthly electricity consumption of the billable building.
     *
     * @return The monthly electricity consumption of the building in kilowatt-hours (kWh).
     */
    double getElectricityConsumption();

    /**
     * Retrieves the monthly natural gas consumption of the billable building.
     *
     * @return The monthly natural gas consumption of the building in cubic meters (m³).
     */
    double getNaturalGasConsumption();

}
