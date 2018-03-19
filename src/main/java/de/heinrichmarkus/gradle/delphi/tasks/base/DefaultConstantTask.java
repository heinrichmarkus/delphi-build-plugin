package de.heinrichmarkus.gradle.delphi.tasks.base;

import de.heinrichmarkus.gradle.delphi.utils.delphi.DelphiUnit;
import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;

import java.io.File;

public class DefaultConstantTask extends DefaultTask {
    final Property<String> fileName = getProject().getObjects().property(String.class);
    protected final Property<String> constantName = getProject().getObjects().property(String.class);

    @Input
    public Property<String> getFileName() {
        return fileName;
    }

    @Input
    public Property<String> getConstantName() {
        return constantName;
    }

    protected DelphiUnit createDelphiUnit() {
        if (!fileName.isPresent()) {
            getLogger().lifecycle("No file specified!");
        } else if (!constantName.isPresent()) {
            getLogger().lifecycle("No constant specified!");
        }
        return new DelphiUnit(new File(fileName.get()));
    }
}
