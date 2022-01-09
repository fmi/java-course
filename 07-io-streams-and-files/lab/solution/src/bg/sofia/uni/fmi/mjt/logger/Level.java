package bg.sofia.uni.fmi.mjt.logger;

public enum Level {
    ERROR(4), WARN(3), INFO(2), DEBUG(1);

    private final int level;

    Level(int level) {
        this.level = level;
    }

    int getLevel() {
        return level;
    }
}
