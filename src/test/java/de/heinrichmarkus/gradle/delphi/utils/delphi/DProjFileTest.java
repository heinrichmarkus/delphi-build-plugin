package de.heinrichmarkus.gradle.delphi.utils.delphi;

import de.heinrichmarkus.gradle.delphi.utils.SoftwareVersion;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class DProjFileTest {
    private File projectFile = new File("build/resources/test/PGradle_Client.dproj");

    @Test
    public void writeVersion() throws Exception {
        File projectFileCopy = createFreshFileCopy();
        DProjFile dproj = new DProjFile(projectFileCopy);
        SoftwareVersion softwareVersion = new SoftwareVersion("1.2.3");
        dproj.writeVersion(softwareVersion);

        String text = new String(Files.readAllBytes(projectFileCopy.toPath()), StandardCharsets.UTF_8);
        Assert.assertTrue("CFBundleVersion", text.contains("CFBundleVersion=1.2.3;"));
        Assert.assertTrue("FileVersion",text.contains("FileVersion=1.2.3.0;"));
        Assert.assertTrue("ProductVersion",text.contains("ProductVersion=1.2.3.0;"));
        Assert.assertTrue("versionName", text.contains("versionName=1.2.3;"));
        Assert.assertTrue("VerInfo_MajorVer", text.contains("<VerInfo_MajorVer>1</VerInfo_MajorVer>"));
        Assert.assertTrue("VerInfo_MinorVer", text.contains("<VerInfo_MinorVer>2</VerInfo_MinorVer>"));
        Assert.assertTrue("VerInfo_Build", text.contains("<VerInfo_Build>3</VerInfo_Build>"));
        Assert.assertTrue("VerInfo_Release", text.contains("<VerInfo_Release>3</VerInfo_Release>"));
    }

    @Test
    public void writeVersionCode() throws Exception {
        File projectFileCopy = createFreshFileCopy();
        DProjFile dproj = new DProjFile(projectFileCopy);
        dproj.writeVersionCode(9537);

        String text = new String(Files.readAllBytes(projectFileCopy.toPath()), StandardCharsets.UTF_8);
        Assert.assertTrue("versionCode", text.contains("versionCode=9537;"));
    }

    @Test
    public void selectConfigAndPlatform() throws Exception {
        File projectFileCopy = createFreshFileCopy();
        DProjFile dproj = new DProjFile(projectFileCopy);
        dproj.setConfigAndPlatform("Debug123", "Win64Test");

        String text = new String(Files.readAllBytes(projectFileCopy.toPath()), StandardCharsets.UTF_8);
        Assert.assertTrue("Config", text.contains("<Config Condition=\"'$(Config)'==''\">Debug123</Config>"));
        Assert.assertTrue("Platform", text.contains("<Platform Condition=\"'$(Platform)'==''\">Win64Test</Platform>"));
    }

    private File createFreshFileCopy() {
        File projectFileCopy = new File("build/resources/test/PGradle_Client-Copy.dproj");
        try {
            Files.copy(projectFile.toPath(), projectFileCopy.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return projectFileCopy;
    }
}