package de.heinrichmarkus.gradle.delphi.tasks;

import de.heinrichmarkus.gradle.delphi.tasks.base.DefaultWriteConstantTask;
import de.heinrichmarkus.gradle.delphi.utils.SoftwareVersion;
import org.gradle.api.provider.PropertyState;
import org.gradle.api.tasks.Input;

import java.util.Calendar;

public class WriteVersionConstantTask extends DefaultWriteConstantTask {
    protected final PropertyState<String> version = getProject().property(String.class);
    protected final PropertyState<Boolean> noDate = getProject().property(Boolean.class);
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
    public PropertyState<String> getVersion() {
        return version;
    }

    @Input
    public PropertyState<Boolean> getNoDate() {
        return noDate;
    }
}
