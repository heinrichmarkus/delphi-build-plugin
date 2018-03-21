package de.heinrichmarkus.gradle.delphi.extensions.msbuild;

public class MsbuildItem {
    private String file;
    private String config;
    private String platform;
    private String target;

    public MsbuildItem(String file, String config, String platform) {
        this.file = file;
        this.config = config;
        this.platform = platform;
    }

    public MsbuildItem(String file, String config, String platform, String target) {
        this.file = file;
        this.config = config;
        this.platform = platform;
        this.target = target;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(file).append(" (").append(config).append(":").append(platform);
        if (target != null && !target.isEmpty()) {
            sb.append(":").append(target);
        }
        sb.append(")");
        return sb.toString();
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
