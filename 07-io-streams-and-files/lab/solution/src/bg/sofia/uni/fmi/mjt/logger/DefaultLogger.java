package bg.sofia.uni.fmi.mjt.logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

public class DefaultLogger implements Logger {

    private static final String LOG_FORMAT = "[%s]|%s|%s|%s";
    private static final String LOG_FILE_NAME_FORMAT = "logs-%d.txt";

    private static long logFileSuffix = 0;

    private final LoggerOptions options;

    private Path logFilePath;
    private BufferedWriter bufferedLogsWriter;

    public DefaultLogger(LoggerOptions options) {
        this.options = options;
        this.logFilePath = generateLogFilePath();
        try {
            openLogFile();
        } catch (IOException e) {
            if (options.shouldThrowErrors()) {
                throw new LogException("failed to log", e);
            }
        }
    }

    @Override
    public LoggerOptions getOptions() {
        return options;
    }

    @Override
    public void log(Level logLevel, LocalDateTime timestamp, String message) {
        checkNull(logLevel, "logLevel");
        checkNull(timestamp, "timestamp");
        checkEmpty(message, "message");

        try {
            if (logLevel.getLevel() < options.getMinLogLevel().getLevel()) {
                return;
            }

            if (isFileTooBig()) {
                closeLogFile();
                logFilePath = generateLogFilePath();
                openLogFile();
            }

            String logMessage =
                String.format(LOG_FORMAT, logLevel.name(), timestamp, options.getClazz().getPackageName(), message);
            writeToLogFile(logMessage);
        } catch (IOException e) {
            if (options.shouldThrowErrors()) {
                throw new LogException("failed to log", e);
            }
        }
    }

    public Path getCurrentFilePath() {
        return logFilePath;
    }

    private Path generateLogFilePath() {
        return Path.of(options.getDirectory(), String.format(LOG_FILE_NAME_FORMAT, logFileSuffix++));
    }

    private boolean isFileTooBig() throws IOException {
        return Files.size(logFilePath) >= options.getMaxFileSizeBytes();
    }

    private void openLogFile() throws IOException {
        bufferedLogsWriter =
            Files.newBufferedWriter(logFilePath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    private void writeToLogFile(String log) throws IOException {
        bufferedLogsWriter.write(log + System.lineSeparator());
        bufferedLogsWriter.flush();
    }

    private void closeLogFile() throws IOException {
        bufferedLogsWriter.close();
    }

    private void checkNull(Object o, String field) {
        if (o == null) {
            throw new IllegalArgumentException(String.format("%s cannot be null", field));
        }
    }

    private void checkEmpty(String s, String field) {
        checkNull(s, field);
        if (s.isEmpty()) {
            throw new IllegalArgumentException(String.format("%s cannot be empty", field));
        }
    }
}
