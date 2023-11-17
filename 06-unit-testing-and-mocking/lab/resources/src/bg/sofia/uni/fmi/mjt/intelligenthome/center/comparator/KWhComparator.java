package bg.sofia.uni.fmi.mjt.intelligenthome.center.comparator;

import bg.sofia.uni.fmi.mjt.intelligenthome.device.IoTDevice;

import java.util.Comparator;

public class KWhComparator implements Comparator<IoTDevice> {

    @Override
    public int compare(IoTDevice firstDevice, IoTDevice secondDevice) {
        return (int) (secondDevice.getPowerConsumptionKWh() - firstDevice.getPowerConsumptionKWh());
    }

}
