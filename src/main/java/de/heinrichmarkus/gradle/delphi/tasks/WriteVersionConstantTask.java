package de.heinrichmarkus.gradle.delphi.tasks;

import de.heinrichmarkus.gradle.delphi.tasks.base.DefaultWriteConstantTask;
import de.heinrichmarkus.gradle.delphi.utils.SoftwareVersion;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;

import java.util.Calendar;

public class WriteVersionConstantTask extends DefaultWriteConstantTask {
    private final Property<String> version = getProject().getObjects().property(String.class);
    private final Property<Integer> buildNumberParam = getProject().getObjects().property(Integer.class);
    private final Property<Boolean> noDate = getProject().getObjects().property(Boolean.class);

    protected String calculateValue() {
        SoftwareVersion sv = new SoftwareVersion(version.get());
        sv.setDate(Calendar.getInstance());
        if (buildNumberParam.isPresent()) {
            sv.setBuild(buildNumberParam.get());
        }
        SoftwareVersion.Format format = SoftwareVersion.Format.FULL;
        if (noDate.get()) {
            format = SoftwareVersion.Format.NO_DATE;
        }
        return sv.format(format);
    }

    @Input
    public Property<String> getVersion() {
        return version;
    }

    public Property<Integer> getBuildNumberParam() {
        return buildNumberParam;
    }

    @Input
    public Property<Boolean> getNoDate() {
        return noDate;
    }
}
