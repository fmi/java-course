package bg.sofia.uni.fmi.mjt.space.mission;

public enum MissionStatus {
    SUCCESS("Success"),
    FAILURE("Failure"),
    PARTIAL_FAILURE("Partial Failure"),
    PRELAUNCH_FAILURE("Prelaunch Failure");

    private final String value;

    MissionStatus(String value) {
        this.value = value;
    }

    public static MissionStatus fromString(String value) {
        for(MissionStatus missionStatus: MissionStatus.values()) {
            if(missionStatus.value.equals(value)) {
                return missionStatus;
            }
        }

        throw new IllegalArgumentException("does not exist" + value);
    }

    public String toString() {
        return value;
    }
}
