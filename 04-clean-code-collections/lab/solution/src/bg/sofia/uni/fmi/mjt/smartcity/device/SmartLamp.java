package bg.sofia.uni.fmi.mjt.smartcity.device;

import java.time.LocalDateTime;

import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

public class SmartLamp extends AbstractSmartDevice {

	public SmartLamp(String name, double powerConsumption, LocalDateTime installationDateTime) {
		super(name, powerConsumption, installationDateTime);
	}

	@Override
	public DeviceType getType() {
		return DeviceType.LAMP;
	}

}
