package de.heinrichmarkus.gradle.delphi.extensions.msbuild;

import java.util.ArrayList;
import java.util.List;

public class MsbuildItemPrepared extends MsbuildItem {
    private String config;
    private String platform;
    private String target;
    private String buildType;

    public MsbuildItemPrepared(String file, String config, String platform) {
        this.file = file;
        this.config = config;
        this.platform = platform;
    }

    public MsbuildItemPrepared(String file, String config, String platform, String target) {
        this(file, config, platform);
        this.target = target;
        this.buildType = "AppStore"; // Default
    }

    public MsbuildItemPrepared(String file, String config, String platform, String target, String buildType) {
        this(file, config, platform, target);
        this.buildType = buildType;
    }

    @Override
    public String getMsbuildParameters() {
        List<String> parts = new ArrayList<>();
        parts.add(getFile());
        parts.add(toMsbuildTarget("Build"));
        parts.add(toMsbuildTarget(getTarget()));
        parts.add(toMsbuildProperty("config", getConfig()));
        parts.add(toMsbuildProperty("platform", getPlatform()));
        parts.add(toMsbuildProperty("BT_BuildType", getBuildType()));

        StringBuilder command = new StringBuilder();
        for (int i = 0; i < parts.size(); i++) {
            if (parts.get(i) != null && !parts.get(i).isEmpty()) {
                command.append(parts.get(i) + " ");
            }
        }
        return command.toString().trim();
    }

    private String toMsbuildTarget(String targetName) {
        if (targetName != null && !targetName.isEmpty()) {
            return "/t:" + targetName;
        } else {
            return "";
        }
    }

    private String toMsbuildProperty(String paramName, String paramValue) {
        if (paramName != null && paramValue != null && !paramName.isEmpty() && !paramValue.isEmpty()) {
            return String.format("/p:%s=%s", paramName, paramValue);
        } else {
            return "";
        }
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

    public String getBuildType() {
        return buildType;
    }

    public void setBuildType(String buildType) {
        this.buildType = buildType;
    }
}
