package de.heinrichmarkus.gradle.delphi.utils;

import de.heinrichmarkus.gradle.delphi.utils.SoftwareVersion;
import de.heinrichmarkus.gradle.delphi.utils.exceptions.SoftwareVersionParseException;
import org.junit.Test;

import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

public class SoftwareVersionTest {
    @Test
    public void testParseWithDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SoftwareVersion version = new SoftwareVersion("1.14.2-2017-06-16_084521");
        assertEquals(1, version.getMajor());
        assertEquals(14, version.getMinor());
        assertEquals(2, version.getPatch());
        assertEquals("2017-06-16 08:45:21", sdf.format(version.getDate().getTime()));
    }

    @Test
    public void testParseWithoutDate() {
        SoftwareVersion version = new SoftwareVersion("1.14.2");
        assertEquals(1, version.getMajor());
        assertEquals(14, version.getMinor());
        assertEquals(2, version.getPatch());
    }

    @Test
    public void testParseException() {
        try {
            SoftwareVersion version = new SoftwareVersion("1.14.1-2017.06.16.084521");
            fail();
        } catch (SoftwareVersionParseException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testToString() {
        String versionStr = "1.14.2-2017-06-16_084521";
        SoftwareVersion version = new SoftwareVersion(versionStr);
        assertEquals(versionStr, version.toString());
    }

    @Test
    public void testFormatSort() {
        String versionStr = "1.14.2-2017-06-16_084521";
        SoftwareVersion version = new SoftwareVersion(versionStr);
        assertEquals("1.14.2", version.format(SoftwareVersion.Format.SHORT));
    }
}