package de.heinrichmarkus.gradle.delphi.utils.logger;

import de.heinrichmarkus.gradle.delphi.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FileLogger implements DbpLogger {
    private final File file;
    private final List<String> messages = new ArrayList<>();

    public FileLogger(String file) {
        this(new File(file));
    }

    public FileLogger(File file) {
        this.file = file;
    }

    @Override
    public void log(String message) {
        messages.add(formatMessage(message));
    }

    private String formatMessage(String message) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return String.format("%s: %s", sdf.format(Calendar.getInstance().getTime()), message);
    }

    public void flush() {
        createFileIfNotExists();
        Utils.appendAllLines(file, messages);
    }

    private void createFileIfNotExists() {
        if (!file.exists()) {
            try {
                Files.createDirectories(file.getParentFile().getAbsoluteFile().toPath());
                Files.createFile(file.getAbsoluteFile().toPath());
            } catch (IOException e) {
                throw new FileLoggerException(String.format("Couldn't create log file %s", file.getAbsolutePath()), e);
            }
        }
    }

    private class FileLoggerException extends RuntimeException {
        private FileLoggerException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
