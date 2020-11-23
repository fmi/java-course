package bg.sofia.uni.fmi.mjt.smartcity.enums;

public enum DeviceType {

	TRAFFIC_LIGHT("TFL"), LAMP("LM"), CAMERA("CM");

	private final String shortName;

	private DeviceType(String shortName) {
		this.shortName = shortName;
	}

	public String getShortName() {
		return shortName;
	}

}