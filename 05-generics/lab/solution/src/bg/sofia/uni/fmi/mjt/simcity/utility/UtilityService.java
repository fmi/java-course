package bg.sofia.uni.fmi.mjt.simcity.utility;

import bg.sofia.uni.fmi.mjt.simcity.Assert;
import bg.sofia.uni.fmi.mjt.simcity.property.billable.Billable;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class UtilityService implements UtilityServiceAPI {

    private final Map<UtilityType, Double> taxRates;

    public UtilityService(Map<UtilityType, Double> taxRates) {
        this.taxRates = taxRates;
    }

    @Override
    public double getUtilityCosts(UtilityType utilityType, Billable billable) {
        Assert.notNull(utilityType);
        Assert.notNull(billable);

        return switch (utilityType) {
            case WATER -> calculateWaterCostsForBuilding(billable);
            case ELECTRICITY -> calculateElectricityCostsForBuilding(billable);
            case NATURAL_GAS -> calculateNaturalGasCostsForBuilding(billable);
        };
    }

    private double calculateWaterCostsForBuilding(Billable billable) {
        return taxRates.get(UtilityType.WATER) * billable.getWaterConsumption();
    }

    private double calculateElectricityCostsForBuilding(Billable billable) {
        return taxRates.get(UtilityType.ELECTRICITY) * billable.getElectricityConsumption();
    }

    private double calculateNaturalGasCostsForBuilding(Billable billable) {
        return taxRates.get(UtilityType.NATURAL_GAS) * billable.getNaturalGasConsumption();
    }

    @Override
    public double getTotalUtilityCosts(Billable billable) {
        Assert.notNull(billable);

        double totalUtilityCost = 0.0d;

        for (UtilityType utilityType : UtilityType.values()) {
            totalUtilityCost += getUtilityCosts(utilityType, billable);
        }

        return totalUtilityCost;
    }

    @Override
    public Map<UtilityType, Double> computeCostsDifference(Billable firstBillable, Billable secondBillable) {
        Assert.notNull(firstBillable);
        Assert.notNull(secondBillable);

        Map<UtilityType, Double> costsDifference = new EnumMap<>(UtilityType.class);

        for (UtilityType utilityType : UtilityType.values()) {
            double lhsCost = getUtilityCosts(utilityType, firstBillable);
            double rhsCost = getUtilityCosts(utilityType, secondBillable);

            double costDifference = Math.abs(lhsCost - rhsCost);

            costsDifference.putIfAbsent(utilityType, costDifference);
        }

        return Collections.unmodifiableMap(costsDifference);
    }

}
