package de.heinrichmarkus.gradle.delphi.utils;

import org.gradle.api.logging.Logger;

import java.util.List;

public class LogUtils {
    private LogUtils() {
        // hide
    }

    public static void logEnvVars(Logger logger, List<String> envVars) {
        for (String item : envVars)
            logger.info("\t\t- " + item);
    }
}
