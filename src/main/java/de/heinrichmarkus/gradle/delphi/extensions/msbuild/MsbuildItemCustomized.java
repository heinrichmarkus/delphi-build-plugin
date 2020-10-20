package de.heinrichmarkus.gradle.delphi.extensions.msbuild;

public class MsbuildItemCustomized extends MsbuildItem {
    private String customOptions;

    public MsbuildItemCustomized(String file, String customOptions) {
        this.file = file;
        this.customOptions = customOptions;
    }

    @Override
    public String getMsbuildParameters() {
        String params = getFile();
        if (customOptions != null && !customOptions.isEmpty()) {
            params += " " + customOptions.trim();
        }
        return params;
    }
}
