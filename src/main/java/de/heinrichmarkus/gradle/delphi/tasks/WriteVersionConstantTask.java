package de.heinrichmarkus.gradle.delphi.tasks;

import de.heinrichmarkus.gradle.delphi.tasks.base.DefaultWriteConstantTask;
import de.heinrichmarkus.gradle.delphi.utils.SoftwareVersion;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;

import java.util.Calendar;

public class WriteVersionConstantTask extends DefaultWriteConstantTask {
    private final Property<String> version = getProject().getObjects().property(String.class);
    private final Property<Boolean> noDate = getProject().getObjects().property(Boolean.class);
    protected String calculateValue() {
        SoftwareVersion softwareVersion = new SoftwareVersion(version.get());
        softwareVersion.setDate(Calendar.getInstance());
        SoftwareVersion.Format format = SoftwareVersion.Format.FULL;
        if (noDate.get()) {
            format = SoftwareVersion.Format.SHORT;
        }
        return softwareVersion.format(format);
    }

    @Input
    public Property<String> getVersion() {
        return version;
    }

    @Input
    public Property<Boolean> getNoDate() {
        return noDate;
    }
}
