package de.heinrichmarkus.gradle.delphi.extensions.msbuild;

import org.junit.Test;

import static org.junit.Assert.*;

public class MsbuildItemCustomizedTest {

    @Test
    public void getMsbuildParameters() {
        MsbuildItem item = new MsbuildItemCustomized("Project.dproj", "/t:Build /p:config=Debug /p:platform=Win32");
        assertEquals("Project.dproj /t:Build /p:config=Debug /p:platform=Win32", item.getMsbuildParameters());
    }
}