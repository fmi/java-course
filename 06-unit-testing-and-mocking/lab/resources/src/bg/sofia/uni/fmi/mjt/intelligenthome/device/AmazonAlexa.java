package bg.sofia.uni.fmi.mjt.intelligenthome.device;

import java.time.LocalDateTime;

public class AmazonAlexa extends IoTDeviceBase {
    private String alexaID;
    private DeviceType type;

    public AmazonAlexa(String name, double powerConsumption, LocalDateTime installationDateTime) {
        super(name, powerConsumption, installationDateTime);
        // super.setType(DeviceType.SMART_SPEAKER);
        // super.setDeviceID(uniqueNumberDevice);
        // = super.deviceID;


        type = DeviceType.SMART_SPEAKER;
        alexaID = type.getShortName() + '-' + name + '-' + uniqueNumberDevice;
        uniqueNumberDevice++;
    }

    @Override
    public DeviceType getType() {
        return type;
    }

    @Override
    public String getId() {
        return alexaID;
    }

}
