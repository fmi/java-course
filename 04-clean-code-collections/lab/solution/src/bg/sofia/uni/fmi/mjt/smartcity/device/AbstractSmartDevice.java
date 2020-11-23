package bg.sofia.uni.fmi.mjt.smartcity.device;

import java.time.LocalDateTime;

import bg.sofia.uni.fmi.mjt.smartcity.device.id.DeviceIdGenerator;
import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

public abstract class AbstractSmartDevice implements SmartDevice {

	private final String id;
	private final String name;
	private final double powerConsumption;
	private final LocalDateTime installationDateTime;

	protected AbstractSmartDevice(String name, double powerConsumption, LocalDateTime installationDateTime) {
		this.name = name;
		this.powerConsumption = powerConsumption;
		this.installationDateTime = installationDateTime;
		this.id = generateId();
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public double getPowerConsumption() {
		return powerConsumption;
	}

	@Override
	public LocalDateTime getInstallationDateTime() {
		return installationDateTime;
	}

	@Override
	public abstract DeviceType getType();

	private String generateId() {
		return DeviceIdGenerator.generate(getType(), name);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AbstractSmartDevice other = (AbstractSmartDevice) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
