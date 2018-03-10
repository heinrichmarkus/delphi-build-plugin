package de.heinrichmarkus.gradle.delphi.utils.delphi;

public class DelphiInstallation {
    private String version;
    private String rootDir;

    public DelphiInstallation(String version, String rootDir) {
        this.version = version;
        this.rootDir = rootDir;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", version, rootDir);
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRootDir() {
        return rootDir;
    }

    public void setRootDir(String rootDir) {
        this.rootDir = rootDir;
    }
}
