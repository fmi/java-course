package bg.sofia.uni.fmi.mjt.smartcity.device.id;

import java.util.EnumMap;
import java.util.Map;

import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

public class DeviceIdGenerator {

	private static final Map<DeviceType, Integer> ID_NUMBERS = new EnumMap<>(DeviceType.class);
	private static final String DASH = "-";

	public static String generate(DeviceType type, String name) {
		if (!ID_NUMBERS.containsKey(type)) {
			ID_NUMBERS.put(type, 0);
		}

		Integer currentIdNumber = ID_NUMBERS.get(type);
		ID_NUMBERS.put(type, currentIdNumber + 1);

		return type.getShortName() + DASH + name + DASH + currentIdNumber;
	}

}
