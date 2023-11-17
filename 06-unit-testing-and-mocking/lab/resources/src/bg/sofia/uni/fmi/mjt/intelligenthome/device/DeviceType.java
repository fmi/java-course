package bg.sofia.uni.fmi.mjt.intelligenthome.device;

public enum DeviceType {
    SMART_SPEAKER("SPKR"), BULB("BLB"), THERMOSTAT("TMST");

    private final String shortName = "";

    private DeviceType(String shortName) {
        shortName = this.shortName;
    }

    public String getShortName() {
        return shortName;
    }
}
