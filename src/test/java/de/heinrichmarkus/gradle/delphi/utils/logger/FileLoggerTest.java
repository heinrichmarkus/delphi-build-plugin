package de.heinrichmarkus.gradle.delphi.utils.logger;

import org.junit.Test;

import java.io.File;
import java.nio.file.Files;

import static org.junit.Assert.*;

public class FileLoggerTest {
    @Test
    public void createDir() throws Exception {
        File file = new File("build/resources/test/sub/log/test.log");
        Files.deleteIfExists(file.toPath());
        Files.deleteIfExists(file.getParentFile().toPath());
        Files.deleteIfExists(file.getParentFile().getParentFile().toPath());
        FileLogger logger = new FileLogger("build/resources/test/sub/log/test.log");
        logger.log("test");
        logger.flush();
        assertTrue(file.getParentFile().exists());
        assertTrue(file.exists());
    }
}