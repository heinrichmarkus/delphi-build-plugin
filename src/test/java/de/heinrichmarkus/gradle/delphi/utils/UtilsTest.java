package de.heinrichmarkus.gradle.delphi.utils;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class UtilsTest {
    @Test
    public void testCountFiles() {
        File dir = new File("src/test/resources/git");
        assertEquals(3, Utils.countFiles(dir));
    }

}