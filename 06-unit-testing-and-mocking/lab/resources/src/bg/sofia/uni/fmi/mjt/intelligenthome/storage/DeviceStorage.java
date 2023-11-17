package bg.sofia.uni.fmi.mjt.intelligenthome.storage;

import bg.sofia.uni.fmi.mjt.intelligenthome.device.IoTDevice;

import java.util.Collection;


public interface DeviceStorage {
    IoTDevice get(String id);

    IoTDevice store(String id, IoTDevice device);

    boolean exists(String id);

    boolean delete(String id);

    Collection<IoTDevice> listAll();
}
