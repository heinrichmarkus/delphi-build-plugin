package de.heinrichmarkus.gradle.delphi.utils;

import de.heinrichmarkus.gradle.delphi.utils.exceptions.SoftwareVersionParseException;
import org.junit.Test;

import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

public class SoftwareVersionTest {
    @Test
    public void parseWithDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SoftwareVersion version = new SoftwareVersion("1.14.2-2017-06-16_084521");
        assertEquals(1, version.getMajor());
        assertEquals(14, version.getMinor());
        assertEquals(2, version.getPatch());
        assertEquals("2017-06-16 08:45:21", sdf.format(version.getDate().getTime()));
    }

    @Test
    public void parseWithoutDate() {
        SoftwareVersion version = new SoftwareVersion("1.14.2");
        assertEquals(1, version.getMajor());
        assertEquals(14, version.getMinor());
        assertEquals(2, version.getPatch());
    }

    @Test
    public void parse4Digits() {
        SoftwareVersion version = new SoftwareVersion("1.17.2.573");
        assertEquals(1, version.getMajor());
        assertEquals(17, version.getMinor());
        assertEquals(2, version.getPatch());
        assertEquals(573, version.getBuild());
    }

    @Test
    public void parseException() {
        try {
            SoftwareVersion version = new SoftwareVersion("1.14.1-2017.06.16.084521");
            fail();
        } catch (SoftwareVersionParseException e) {
            assertTrue(true);
        }
    }

    @Test
    public void convertToString() {
        String versionStr = "1.14.2-2017-06-16_084521";
        SoftwareVersion version = new SoftwareVersion(versionStr);
        assertEquals(versionStr, version.toString());
    }

    @Test
    public void convertToShortString() {
        String versionStr = "1.14.2-2017-06-16_084521";
        SoftwareVersion version = new SoftwareVersion(versionStr);
        assertEquals("1.14.2", version.format(SoftwareVersion.Format.SHORT));
    }

    @Test
    public void convertToStringWithBuildNumber() {
        String versionStr = "1.14.2.123-2017-06-16_084521";
        SoftwareVersion version = new SoftwareVersion(versionStr);
        assertEquals(versionStr, version.toString());
    }

    @Test
    public void convertToShortStringWithBuildNumber() {
        String versionStr = "1.14.2.123-2017-06-16_084521";
        SoftwareVersion version = new SoftwareVersion(versionStr);
        assertEquals("1.14.2.123", version.format(SoftwareVersion.Format.SHORT));
    }

    @Test
    public void setBuildLater() {
        String versionStr = "1.14.2";
        SoftwareVersion version = new SoftwareVersion(versionStr);
        version.setBuild(123);
        assertEquals("1.14.2.123", version.format(SoftwareVersion.Format.SHORT));
    }
}