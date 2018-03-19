package de.heinrichmarkus.gradle.delphi.extensions.msbuild;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MsbuildConfiguration {
    private final List<MsbuildItem> items;

    public MsbuildConfiguration() {
        items = new ArrayList<>();
    }

    public void add(String file, String config, String platform) {
        items.add(new MsbuildItem(file, config, platform));
    }

    public void add(String file, String config, String platform, String target) {
        items.add(new MsbuildItem(file, config, platform, target));
    }

    public List<MsbuildItem> getItems() {
        return Collections.unmodifiableList(items);
    }
}
