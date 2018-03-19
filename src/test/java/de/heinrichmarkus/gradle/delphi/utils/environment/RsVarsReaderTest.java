package de.heinrichmarkus.gradle.delphi.utils.environment;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class RsVarsReaderTest {
    @Test
    public void testSize() {
        RsVarsReader reader = new RsVarsReader(new File("build/resources/test/rsvars.bat"));
        assertEquals(10, reader.size());
    }
    @Test
    public void testRead() {
        RsVarsReader reader = new RsVarsReader(new File("build/resources/test/rsvars.bat"));
        assertEquals("DE", reader.read("LANGDIR"));
        assertEquals("C:\\Program Files (x86)\\Embarcadero\\Studio\\19.0\\include", reader.read("BDSINCLUDE"));
    }

    @Test
    public void testReadException() {
        RsVarsReader reader = new RsVarsReader(new File("build/resources/test/rsvars.bat"));
        try {
            reader.read("doesnotexist");
            fail();
        } catch (EnvironmentVariableNotFound e) {
            assertTrue(true);
        }
    }
}