package bg.sofia.uni.fmi.mjt.simcity.property;

import bg.sofia.uni.fmi.mjt.simcity.property.buildable.BuildableType;

public final class ResidentialProperty extends Property {

    private static final double AVERAGE_MONTHLY_WATER_CONSUMPTION_IN_CUBIC_METERS = 54.00;
    private static final double AVERAGE_MONTHLY_ELECTRICITY_CONSUMPTION_IN_KWH = 900.00;
    private static final double AVERAGE_MONTHLY_NATURAL_GAS_CONSUMPTION_IN_CUBIC_METERS = 174.00;

    private final int area;

    public ResidentialProperty(int area) {
        super(BuildableType.RESIDENTIAL);
        this.area = area;
    }

    @Override
    public int getArea() {
        return area;
    }

    @Override
    public double getWaterConsumption() {
        return AVERAGE_MONTHLY_WATER_CONSUMPTION_IN_CUBIC_METERS;
    }

    @Override
    public double getElectricityConsumption() {
        return AVERAGE_MONTHLY_ELECTRICITY_CONSUMPTION_IN_KWH;
    }

    @Override
    public double getNaturalGasConsumption() {
        return AVERAGE_MONTHLY_NATURAL_GAS_CONSUMPTION_IN_CUBIC_METERS;
    }

}
