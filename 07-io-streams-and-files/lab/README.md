# Logger :scroll:

Професионалните приложения обикновено използват *logging framework* (или накратко, *logger*), за да записват информация (най-често във файл), която служи за проследяване на изпълнението, откриване и отстраняване на проблеми, отбелязване на важни за приложението събития и т.н. Използването на framework за целта е улеснение, прави кода по-четим, често по-ефективен, а също и стандартизира формата на съобщенията, така че да е възможна автоматизираната им обработка и да се улесни търсенето и филтрирането на събщенията.

Самото JDK предоставя logging framework, в пакета [`java.util.logging`](https://docs.oracle.com/en/java/javase/17/core/java-logging-overview.html), има и множество популярни open-source Java logging frameworks, като [Log4j](https://logging.apache.org/log4j/2.x/).

Ще създадем наш прост logging framework, който ще предоставя базови възможности както за log-ване, така и за извличане и филтриране на log съобщения.

### Logger

В пакета `bg.sofia.uni.fmi.mjt.logger` създайте клас `DefaultLogger`, който има конструктор

```java
public DefaultLogger(LoggerOptions options)
```
и имплементира интерфейса `Logger`:

```java
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
     * @throws LogException             if LoggerOptions.shouldThrowErrors() is true and if the operation cannot be completed
     *                                  In case LoggerOptions.shouldThrowErrors() is false, the method should suppress any problems
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
```

### Logger Options

`Logger`-ът има множество задължителни и опционални параметри:
  - текущия клас (задължителен)
  - името на директорията, където да се записват log файловете (задължителен)
  - максималният размер (в байтове) на log файла (опционален, по подразбиране 1024 байта)
  - да хвърля или игнорира евентуални изключения при самото log-ване (опционален, по подразбиране - да игнорира)
  - минимален *log level* (опционален, по подразбиране INFO)

Параметрите се описват от класа `LoggerOptions` в пакета `bg.sofia.uni.fmi.mjt.logger:`

```java
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
```

### Употреба

Примерна употреба на нашия logging framework би изглеждала така:

```java
package com.cryptobank.investment;

// [...]

public class InvestmentWallet {

    private Logger logger = new DefaultLogger(new LoggerOptions(this.getClass(), "/logs"));

    // [...]

    void buyAsset() {
        // [...]
        logger.log(Level.INFO, LocalDateTime.now(), String.format("Bought asset with ID %s", assetId));
    }

}
```

### Log файлове

Лог файловете са текстови файлове, които се създават в избраната директория. Имената им се състоят от фиксиран префикс, "logs-", пореден номер, започващ от 0 и разширение ".txt". Във всеки момент от времето, точно един от лог файловете е активен (текущ): този с най-големия пореден номер. Файловете имат фиксиран максимален размер. При опит да се запише съобщение, ако текущият лог файл е достигнал или надхвърлил максималния размер, се създава нов файл със следващ пореден номер, той става активен (текущ) и съобщението се записва в него.

### Log съобщения

Всеки ред от лог файл съдържа едно съобщение във формата

```bash
[level]|timestamp|package|message
```

като съдържа четири полета, с разделител `|` (pipe) и семантика: нивото (log level) на съобщението, timestamp на log-ване на съобщението във формат `DateTimeFormatter.ISO_DATE_TIME`, име на пакет (на класа, в/от който се log-ва даденото съобщение) и накрая самото съобщение.

Примерно съдържание на log файла може да е

```bash
[INFO]|2021-11-27T01:11:03.935601200|com.cryptobank.investment|Bought asset with ID 167305
[WARN]|2021-11-27T01:11:04.099162900|com.cryptobank.investment|Disk space is getting low
[ERROR]|2021-11-27T01:11:04.431274900|com.cryptobank.investment|Failed to process request {1405}
```

Log съобщението се моделира от record-a `Log`:

```java
package bg.sofia.uni.fmi.mjt.logger;

import java.time.LocalDateTime;

public record Log(Level level, LocalDateTime timestamp, String packageName, String message) {
}
```

### Log ниво

Log нивото (понякога наричано и *log severity*) е свойството на дадено съобщение, което отразява неговата важност, критичност и приоритет. Нашият logging framework поддържа четири нива, моделирани от `enum`-a `Level`:

```java
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
```

Всяко ниво има асоциирана числова стойност, като по-голяма стойност означава по-висока важност:

- ERROR - проблем, който се отразява на правилното функциониране на приложението
- WARN - означава нещо неочаквано, което се е случило в приложението и може да е потенциален проблем
- INFO - стандартното ниво индикира, че се е случило някакво събитие, приложението е в определено състояние и т.н.
- DEBUG - информация, която може да е нужна за диагностициране и отстраняване на проблем

### Log Parser

За да извличаме и филтрираме съобщения от текущия лог файл, в пакета `bg.sofia.uni.fmi.mjt.logger` ще създадем клас `DefaultLogParser`, който има конструктор

```java
public DefaultLogParser(Path logsFilePath)
```
и имплементира интерфейса `LogParser`:

```java
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
```

#### Подсказки

- Когато е част от регулярен израз, например аргумент на `String.split()`, символът `|` (pipe) има специална семантика. Ако искаме да се тълкува буквално като `|`, трябва да се escape-не с back slash (`\`). Самият back slash обаче има специална семантика в низовите литерали и също трябва да се escape-не. Поради тези причини, ако искате да split-нете низ по разделител `|`, трябва да използвате `String.split("\\|")`.

### Тестване

Може като начало да тествате ръчно logging framework-a, но очакваме да напишете и ваши unit tests с добър code coverage.

### Пакети

Спазвайте имената на пакетите на всички по-горе описани типове, тъй като в противен случай, решението ви няма да може да бъде автоматично тествано.

```
src
└─ bg.sofia.uni.fmi.mjt.logger
    ├─ DefaultLogger.java
    ├─ DefaultLogParser.java
    ├─ Level.java
    ├─ Log.java
    ├─ LogException.java
    ├─ Logger.java
    ├─ LoggerOptions.java
    └─ LogParser.java
test
└─ bg.sofia.uni.fmi.mjt.logger
    └─ (...)
```
