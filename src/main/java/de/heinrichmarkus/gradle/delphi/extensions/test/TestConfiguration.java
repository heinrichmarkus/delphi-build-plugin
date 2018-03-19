package de.heinrichmarkus.gradle.delphi.extensions.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestConfiguration {
    private final List<TestItem> items;

    public TestConfiguration() {
        items = new ArrayList<>();
    }

    public void add(String executable, String arguments) {
        items.add(new TestItem(executable, arguments));
    }

    public void add(String executable) {
        items.add(new TestItem(executable));
    }

    public List<TestItem> getItems() {
        return Collections.unmodifiableList(items);
    }
}
