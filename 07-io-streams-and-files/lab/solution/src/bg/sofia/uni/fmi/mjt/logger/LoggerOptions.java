package bg.sofia.uni.fmi.mjt.logger;

public class LoggerOptions {

    private static final long DEFAULT_MAX_FILE_SIZE_BYTES = 1024;
    private static final boolean DEFAULT_SHOULD_THROW_ERROR = false;

    private final Class<?> clazz;
    private final String directory;

    private long maxFileSizeBytes = DEFAULT_MAX_FILE_SIZE_BYTES;
    private Level minLogLevel = Level.INFO;
    private boolean shouldThrowErrors = DEFAULT_SHOULD_THROW_ERROR;

    public LoggerOptions(Class<?> clazz, String directory) {
        this.clazz = clazz;
        this.directory = directory;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String getDirectory() {
        return directory;
    }

    public long getMaxFileSizeBytes() {
        return maxFileSizeBytes;
    }

    public void setMaxFileSizeBytes(long maxFileSizeBytes) {
        this.maxFileSizeBytes = maxFileSizeBytes;
    }

    public Level getMinLogLevel() {
        return minLogLevel;
    }

    public void setMinLogLevel(Level minLogLevel) {
        this.minLogLevel = minLogLevel;
    }

    public boolean shouldThrowErrors() {
        return shouldThrowErrors;
    }

    public void setShouldThrowErrors(boolean shouldThrowErrors) {
        this.shouldThrowErrors = shouldThrowErrors;
    }
}
