package de.heinrichmarkus.gradle.delphi.utils.logger;

import org.gradle.api.logging.LogLevel;
import org.gradle.api.logging.Logger;

public class GradleLogger implements DbpLogger {
    private final Logger logger;
    private final LogLevel logLevel;

    public GradleLogger(Logger logger, LogLevel logLevel) {
        this.logger = logger;
        this.logLevel = logLevel;
    }

    @Override
    public void log(String message) {
        logger.log(logLevel, message);
    }
}
