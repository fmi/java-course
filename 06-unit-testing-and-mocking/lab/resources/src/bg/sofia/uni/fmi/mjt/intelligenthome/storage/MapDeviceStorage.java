package bg.sofia.uni.fmi.mjt.intelligenthome.storage;

import bg.sofia.uni.fmi.mjt.intelligenthome.device.IoTDevice;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MapDeviceStorage implements DeviceStorage {
    private final Map<String, IoTDevice> devices;

    public MapDeviceStorage() {
        devices = new HashMap<>();
    }

    @Override
    public IoTDevice get(String id) {
        return devices.get(id);
    }

    @Override
    public IoTDevice store(String id, IoTDevice device) {
        return devices.put(id, device);
    }

    @Override
    public boolean exists(String id) {
        return devices.containsKey(id);
    }

    @Override
    public boolean delete(String id) {
        IoTDevice device = devices.get(id);
        if (device == null) {
            return false;
        }
        return devices.remove(id, device);
    }

    @Override
    public Collection<IoTDevice> listAll() {
        return devices.values();
    }
}
