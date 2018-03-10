package de.heinrichmarkus.gradle.delphi.extensions.assambly;

import org.gradle.api.Project;
import org.gradle.api.provider.PropertyState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AssemblyConfiguration {
    private final PropertyState<String> name;
    private List<AssemblyItem> items;

    public AssemblyConfiguration(Project project) {
        items = new ArrayList<>();
        name = project.property(String.class);
    }

    public void add(String source) {
        items.add(new AssemblyItem(source));
    }

    public void add(String source, Boolean optional) {
        items.add(new AssemblyItem(source, optional));
    }

    public void add(String source, String destination) {
        items.add(new AssemblyItem(source, destination));
    }

    public void add(String source, String destination, Boolean optional) {
        items.add(new AssemblyItem(source, destination, optional));
    }

    public List<AssemblyItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public PropertyState<String> getName() {
        return name;
    }
}
