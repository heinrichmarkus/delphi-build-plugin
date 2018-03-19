package de.heinrichmarkus.gradle.delphi.utils.delphi;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DelphiLocatorTest {
    @Test
    public void extractVersion() {
        String key = "HKEY_CURRENT_USER\\Software\\Embarcadero\\BDS\\19.0";
        assertEquals("19.0", DelphiLocator.extractVersion(key));
    }

    @Test
    public void extractRootDir() {
        List<String> lines = new ArrayList<>();
        lines.add("");
        lines.add("HKEY_CURRENT_USER\\Software\\Embarcadero\\BDS\\19.0");
        lines.add("    RootDir    REG_SZ    C:\\Program Files (x86)\\Embarcadero\\Studio\\19.0\\");
        assertEquals("C:\\Program Files (x86)\\Embarcadero\\Studio\\19.0\\", DelphiLocator.extractRootDir(lines));
    }
}