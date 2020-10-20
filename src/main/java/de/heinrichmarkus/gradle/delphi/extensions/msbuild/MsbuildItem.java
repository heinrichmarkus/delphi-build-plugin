package de.heinrichmarkus.gradle.delphi.extensions.msbuild;

public abstract class MsbuildItem {
    protected String file;

    public abstract String getMsbuildParameters();

    @Override
    public String toString() {
        return getMsbuildParameters();
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
