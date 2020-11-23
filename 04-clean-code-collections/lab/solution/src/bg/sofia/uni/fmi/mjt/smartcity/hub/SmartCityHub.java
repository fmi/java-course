package bg.sofia.uni.fmi.mjt.smartcity.hub;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bg.sofia.uni.fmi.mjt.smartcity.device.SmartDevice;
import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

public class SmartCityHub {

	private static final String DEVICE_CANNOT_BE_NULL_MESSAGE = "Provided device cannot be null";
	private static final String N_CANNOT_BE_NEGATIVE_MESSAGE = "Provided value for 'n' cannot be negative";

	private final Map<String, SmartDevice> deviceRegistry;
	private final Map<DeviceType, Integer> deviceCounts;

	public SmartCityHub() {
		this.deviceRegistry = new LinkedHashMap<>();
		this.deviceCounts = new EnumMap<>(DeviceType.class);
	}

	/**
	 * Adds a @device to the SmartCityHub.
	 *
	 * @throws IllegalArgumentException         in case @device is null.
	 * @throws DeviceAlreadyRegisteredException in case the @device is already registered.
	 */
	public void register(SmartDevice device) throws DeviceAlreadyRegisteredException {
		if (device == null) {
			throw new IllegalArgumentException(DEVICE_CANNOT_BE_NULL_MESSAGE);
		}

		if (deviceRegistry.containsKey(device.getId())) {
			throw new DeviceAlreadyRegisteredException("Provided device is already contained in the Hub");
		}

		deviceRegistry.put(device.getId(), device);

		DeviceType deviceType = device.getType();
		int currentDeviceTypeCount = 0;
		if (deviceCounts.containsKey(deviceType)) {
			currentDeviceTypeCount = deviceCounts.get(deviceType);
		}

		deviceCounts.put(deviceType, currentDeviceTypeCount + 1);
	}

	/**
	 * Removes the @device from the SmartCityHub.
	 *
	 * @throws IllegalArgumentException in case null is passed.
	 * @throws DeviceNotFoundException  in case the @device is not found.
	 */
	public void unregister(SmartDevice device) throws DeviceNotFoundException {
		if (device == null) {
			throw new IllegalArgumentException(DEVICE_CANNOT_BE_NULL_MESSAGE);
		}

		if (!deviceRegistry.containsKey(device.getId())) {
			throw new DeviceNotFoundException("Provided device is not found in the Hub");
		}

		deviceRegistry.remove(device.getId());

		DeviceType deviceType = device.getType();
		deviceCounts.put(deviceType, deviceCounts.get(deviceType) - 1);
	}

	/**
	 * Returns a SmartDevice with an ID @id.
	 *
	 * @throws IllegalArgumentException in case @id is null.
	 * @throws DeviceNotFoundException  in case device with ID @id is not found.
	 */
	public SmartDevice getDeviceById(String id) throws DeviceNotFoundException {
		if (id == null) {
			throw new IllegalArgumentException("Provided id cannot be null");
		}

		if (!deviceRegistry.containsKey(id)) {
			throw new DeviceNotFoundException("Device with the provided id is not found in the Hub");
		}

		return deviceRegistry.get(id);
	}

	/**
	 * Returns the total number of devices with type @type registered in SmartCityHub.
	 *
	 * @throws IllegalArgumentException in case @type is null.
	 */
	public int getDeviceQuantityPerType(DeviceType type) {
		if (type == null) {
			throw new IllegalArgumentException("Provided type cannot be null");
		}

		int quantity = 0;
		if (deviceCounts.containsKey(type)) {
			quantity = deviceCounts.get(type);
		}

		return quantity;
	}

	/**
	 * Returns a collection of IDs of the top @n devices which consumed the most power from the time of their installation until now.
	 *
	 * The total power consumption of a device is calculated by the hours elapsed between the two LocalDateTime-s: the installation time and the current time (now) multiplied by the stated nominal
	 * hourly power consumption of the device.
	 *
	 * If @n exceeds the total number of devices, return all devices available sorted by the given criterion.
	 *
	 * @throws IllegalArgumentException in case @n is a negative number.
	 */
	public Collection<String> getTopNDevicesByPowerConsumption(int n) {
		if (n < 0) {
			throw new IllegalArgumentException(N_CANNOT_BE_NEGATIVE_MESSAGE);
		}

		if (n > deviceRegistry.size()) {
			n = deviceRegistry.size();
		}

		List<SmartDevice> registeredDevices = new ArrayList<>(deviceRegistry.values());
		Collections.sort(registeredDevices, new Comparator<SmartDevice>() {
			@Override
			public int compare(SmartDevice first, SmartDevice second) {
				double hoursOfWorkFirst = Duration.between(LocalDateTime.now(), first.getInstallationDateTime()).toMinutes() / 60.0;
				Double totalPowerConsumptionFirst = hoursOfWorkFirst * first.getPowerConsumption();

				double hoursOfWorkSecond = Duration.between(LocalDateTime.now(), second.getInstallationDateTime()).toMinutes() / 60.0;
				Double totalPowerConsumptionSecond = hoursOfWorkSecond * second.getPowerConsumption();

				return totalPowerConsumptionFirst.compareTo(totalPowerConsumptionSecond);
			}
		});

		registeredDevices = registeredDevices.subList(0, n);

		List<String> deviceIds = new ArrayList<>();
		for (SmartDevice device : registeredDevices) {
			deviceIds.add(device.getId());
		}

		return deviceIds;
	}

	/**
	 * Returns a collection of the first @n registered devices, i.e the first @n that were added in the SmartCityHub (registration != installation).
	 *
	 * If @n exceeds the total number of devices, return all devices available sorted by the given criterion.
	 *
	 * @throws IllegalArgumentException in case @n is a negative number.
	 */
	public Collection<SmartDevice> getFirstNDevicesByRegistration(int n) {
		if (n < 0) {
			throw new IllegalArgumentException(N_CANNOT_BE_NEGATIVE_MESSAGE);
		}

		if (n > deviceRegistry.size()) {
			n = deviceRegistry.size();
		}

		return new ArrayList<>(deviceRegistry.values()).subList(0, n);
	}

}
