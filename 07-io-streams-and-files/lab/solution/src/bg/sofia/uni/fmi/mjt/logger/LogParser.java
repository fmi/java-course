package bg.sofia.uni.fmi.mjt.logger;

import java.time.LocalDateTime;
import java.util.List;

public interface LogParser {

    /**
     * Extracts the logs with log level {@code level} from the current log file
     *
     * @param level log level
     * @return list of logs with the provided log level
     * @throws IllegalArgumentException if {@code level} is null
     */
    List<Log> getLogs(Level level);

    /**
     * Extracts the logs from {@code from} to {@code to} from the current log file
     *
     * @param from timestamp from
     * @param to   timestamp to
     * @return list of logs from {@code from} to {@code to}
     * @throws IllegalArgumentException if {@code from} or {@code to} is null
     */
    List<Log> getLogs(LocalDateTime from, LocalDateTime to);

    /**
     * Extracts the last {@code n}  lines from the current log file
     *
     * @param n last {@code n}  lines
     * @return list of last {@code n} logs. If {@code n} is zero, return an empty list.
     * If there are fewer than {@code n} lines in the log file, return all of them.
     * @throws IllegalArgumentException if {@code n} is a negative number
     */
    List<Log> getLogsTail(int n);
}
