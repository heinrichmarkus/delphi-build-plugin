package de.heinrichmarkus.gradle.delphi.utils.delphi;

import de.heinrichmarkus.gradle.delphi.utils.SoftwareVersion;
import de.heinrichmarkus.gradle.delphi.utils.Utils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DProjFile {
    private final File file;

    public DProjFile(File file) {
        this.file = file;
    }

    public void writeVersion(SoftwareVersion version) {
        processLines(Arrays.asList(
                new FileVersionLineProcessor(version),
                new ProductVersionLineProcessor(version),
                new BundleVersionLineProcessor(version),
                new BundleShortVersionLineProcessor(version),
                new VersionNameLineProcessor(version),
                new VersionInfoMajorLineProcessor(version),
                new VersionInfoMinoarLineProcessor(version),
                new VersionInfoBuildLineProcessor(version),
                new VersionInfoReleaseLineProcessor(version)
        ));
    }

    public void writeVersionCode(Integer versionCode) {
        processLines(Collections.singletonList(new VersionCodeLineProcessor(versionCode)));
    }

    public void setConfigAndPlatform(String config, String platform) {
        processLines(Arrays.asList(
                new ConfigLineProcessor(config),
                new PlatformLineProcessor(platform)
        ));
    }

    private void processLines(List<LineProcessor> processors) {
        List<String> lines = Utils.readAllLines(file, StandardCharsets.UTF_8);
        for (LineProcessor p : processors) {
            substituteVersion(lines, p);
        }
        Utils.writeAllLines(file, lines, StandardCharsets.UTF_8);
    }

    private void substituteVersion(List<String> lines, LineProcessor processor) {
        for (int i = 0; i < lines.size(); i++) {
            if (processor.matches(lines.get(i))) {
                String l = processor.replace(lines.get(i));
                lines.set(i, l);
            }
        }
    }

    public File getFile() {
        return file;
    }

    interface LineProcessor {
        boolean matches(String line);
        String replace(String line);
    }

    class BaseLineProcessor implements LineProcessor {
        String searchPattern;
        String replacement;

        @Override
        public boolean matches(String line) {
            return line.matches(".*" + searchPattern + ".*");
        }

        @Override
        public String replace(String line) {
            return line.replaceAll(searchPattern, replacement);
        }
    }

    class FileVersionLineProcessor extends BaseLineProcessor {
        FileVersionLineProcessor(SoftwareVersion version) {
            searchPattern = "FileVersion=\\d+\\.\\d+\\.\\d+\\.\\d+;";
            replacement = String.format("FileVersion=%s;", version.format(SoftwareVersion.FormatOption.FORCE_BUILDNUMBER));
        }
    }

    class ProductVersionLineProcessor extends BaseLineProcessor {
        ProductVersionLineProcessor(SoftwareVersion version) {
            searchPattern = "ProductVersion=\\d+\\.\\d+\\.\\d+\\.\\d+;";
            replacement = String.format("ProductVersion=%s;", version.format(SoftwareVersion.FormatOption.FORCE_BUILDNUMBER));
        }
    }

    class VersionNameLineProcessor extends BaseLineProcessor {
        VersionNameLineProcessor(SoftwareVersion version) {
            searchPattern = "versionName=[\\d+\\.]+;";
            replacement = String.format("versionName=%s;", version.format());
        }
    }

    class BundleVersionLineProcessor extends BaseLineProcessor {
        BundleVersionLineProcessor(SoftwareVersion version) {
            searchPattern = "CFBundleVersion=[\\d+\\.]+;";
            replacement = String.format("CFBundleVersion=%s;", version.format());
        }
    }

    class BundleShortVersionLineProcessor extends BaseLineProcessor {
        BundleShortVersionLineProcessor(SoftwareVersion version) {
            searchPattern = "CFBundleShortVersionString=[\\d+\\.]+;";
            replacement = String.format("CFBundleShortVersionString=%s;", version.format());
        }
    }

    class VersionCodeLineProcessor extends BaseLineProcessor {
        VersionCodeLineProcessor(Integer versionCode) {
            searchPattern = "versionCode=\\d+;";
            replacement = String.format("versionCode=%d;", versionCode);
        }
    }

    class VersionInfoMajorLineProcessor extends BaseLineProcessor {
        VersionInfoMajorLineProcessor(SoftwareVersion version) {
            searchPattern = "<VerInfo_MajorVer>\\d+</VerInfo_MajorVer>";
            replacement = String.format("<VerInfo_MajorVer>%d</VerInfo_MajorVer>", version.getMajor());
        }
    }

    class VersionInfoMinoarLineProcessor extends BaseLineProcessor {
        VersionInfoMinoarLineProcessor(SoftwareVersion version) {
            searchPattern = "<VerInfo_MinorVer>\\d+</VerInfo_MinorVer>";
            replacement = String.format("<VerInfo_MinorVer>%d</VerInfo_MinorVer>", version.getMinor());
        }
    }

    class VersionInfoBuildLineProcessor extends BaseLineProcessor {
        VersionInfoBuildLineProcessor(SoftwareVersion version) {
            searchPattern = "<VerInfo_Build>\\d+</VerInfo_Build>";
            replacement = String.format("<VerInfo_Build>%d</VerInfo_Build>", version.getBuild());
        }
    }

    class VersionInfoReleaseLineProcessor extends BaseLineProcessor {
        VersionInfoReleaseLineProcessor(SoftwareVersion version) {
            searchPattern = "<VerInfo_Release>\\d+</VerInfo_Release>";
            replacement = String.format("<VerInfo_Release>%d</VerInfo_Release>", version.getPatch());
        }
    }

    private class ConfigLineProcessor extends BaseLineProcessor  {
        ConfigLineProcessor(String config) {
            searchPattern = "<Config Condition=\"'\\$\\(Config\\)'==''\">[\\d\\w]+</Config>";
            replacement = String.format("<Config Condition=\"'\\$\\(Config\\)'==''\">%s</Config>", config);
        }
    }

    private class PlatformLineProcessor extends BaseLineProcessor {
        PlatformLineProcessor(String platform) {
            searchPattern = "<Platform Condition=\"'\\$\\(Platform\\)'==''\">[\\d\\w]+</Platform>";
            replacement = String.format("<Platform Condition=\"'\\$\\(Platform\\)'==''\">%s</Platform>", platform);
        }
    }
}
