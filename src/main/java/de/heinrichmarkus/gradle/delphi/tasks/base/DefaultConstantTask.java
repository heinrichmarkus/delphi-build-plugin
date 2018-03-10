package de.heinrichmarkus.gradle.delphi.tasks.base;

import de.heinrichmarkus.gradle.delphi.utils.delphi.DelphiUnit;
import org.gradle.api.DefaultTask;
import org.gradle.api.provider.PropertyState;
import org.gradle.api.tasks.Input;

import java.io.File;

public class DefaultConstantTask extends DefaultTask {
    protected final PropertyState<String> fileName = getProject().property(String.class);
    protected final PropertyState<String> constantName = getProject().property(String.class);

    @Input
    public PropertyState<String> getFileName() {
        return fileName;
    }

    @Input
    public PropertyState<String> getConstantName() {
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
