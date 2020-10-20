package de.heinrichmarkus.gradle.delphi.extensions.msbuild;

import org.junit.Test;

import static org.junit.Assert.*;

public class MsbuildItemPreparedTest {

    @Test
    public void getMsbuildParametersWin32() {
        MsbuildItem i = new MsbuildItemPrepared("Project.dproj", "Debug", "Win32");
        assertEquals("Project.dproj /t:Build /p:config=Debug /p:platform=Win32", i.getMsbuildParameters());
    }

    @Test
    public void getMsbuildParametersAndroidDeploy() {
        MsbuildItem i = new MsbuildItemPrepared("Project.dproj", "Release", "Android", "Deploy");
        assertEquals("Project.dproj /t:Build /t:Deploy /p:config=Release /p:platform=Android /p:BT_BuildType=AppStore", i.getMsbuildParameters());
    }

    @Test
    public void getMsbuildParametersAndroidDeployAdHoc() {
        MsbuildItem i = new MsbuildItemPrepared("Project.dproj", "Release", "iOSDevice64", "Deploy", "AdHoc");
        assertEquals("Project.dproj /t:Build /t:Deploy /p:config=Release /p:platform=iOSDevice64 /p:BT_BuildType=AdHoc", i.getMsbuildParameters());
    }
}