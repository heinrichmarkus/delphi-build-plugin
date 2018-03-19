package de.heinrichmarkus.gradle.delphi.utils.logger;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class FileLoggerTest {
    @Test
    public void createDir() {
        File file = new File("build/resources/test/log/test.log");
        if (file.exists()) {
            file.delete();
        }
        if (file.getParentFile().exists()) {
            file.getParentFile().delete();
        }
        FileLogger logger = new FileLogger("build/resources/test/log/test.log");
        logger.log("test");
        logger.flush();
        assertTrue(file.getParentFile().exists());
        assertTrue(file.exists());
    }
}