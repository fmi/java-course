package bg.sofia.uni.fmi.mjt.logger;

import java.nio.file.Path;
import java.time.LocalDateTime;

public interface Logger {

    /**
     * Logs message to the current log file. If the currently configured minimum log
     * level is higher than the provided log level, the message should be ignored.
     *
     * @param level     the log message severity
     * @param timestamp the time of logging
     * @param message   log message
     * @throws IllegalArgumentException if {@code level} is null, {@code timestamp} is null
     *                                  or {@code message} is null or empty
     * @throws LogException             if LoggerOptions.shouldThrowErrors() is true
     *                                  and if the operation cannot be completed
     *                                  In case LoggerOptions.shouldThrowErrors() is false,
     *                                  the method should suppress any problems
     */
    void log(Level level, LocalDateTime timestamp, String message);

    /**
     * Gets the Logger's options.
     *
     * @return the Logger's options
     */
    LoggerOptions getOptions();

    /**
     * Gets the current log file path.
     *
     * @return the current log file path
     */
    Path getCurrentFilePath();

}
