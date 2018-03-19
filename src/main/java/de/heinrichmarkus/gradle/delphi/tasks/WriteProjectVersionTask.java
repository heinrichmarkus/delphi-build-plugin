package de.heinrichmarkus.gradle.delphi.tasks;

import de.heinrichmarkus.gradle.delphi.extensions.msbuild.MsbuildItem;
import de.heinrichmarkus.gradle.delphi.extensions.msbuild.MsbuildConfiguration;
import de.heinrichmarkus.gradle.delphi.utils.delphi.DProjFile;
import de.heinrichmarkus.gradle.delphi.utils.SoftwareVersion;
import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WriteProjectVersionTask extends DefaultTask {
    protected final Property<String> version = getProject().getObjects().property(String.class);
    protected final Property<Integer> versionCode = getProject().getObjects().property(Integer.class);
    protected final Property<Boolean> disabled = getProject().getObjects().property(Boolean.class);
    protected MsbuildConfiguration msbuildConfiguration;

    @TaskAction
    public void write() {
        if (!disabled.get()) {
            SoftwareVersion swVersion = new SoftwareVersion(getVersion().get());
            List<File> projectFiles = getProjectFiles();
            for (File f : projectFiles) {
                DProjFile dproj = new DProjFile(f);
                writeVersion(dproj, swVersion);
                writeVersionCode(dproj);
            }
        } else {
            getLogger().lifecycle("Disabled");
        }
    }

    private void writeVersion(DProjFile dproj, SoftwareVersion swVersion) {
        getLogger().lifecycle(String.format("Write version '%s' to %s ", version.get(), dproj.getFile().getAbsolutePath()));
        dproj.writeVersion(swVersion);
    }

    private void writeVersionCode(DProjFile dproj) {
        if (versionCode.get() > 0) {
            getLogger().lifecycle(String.format("Write version-code '%s' to %s ", versionCode.get(), dproj.getFile().getAbsolutePath()));
            dproj.writeVersionCode(versionCode.get());
        }
    }

    private List<File> getProjectFiles() {
        List<String> strings = new ArrayList<>();
        for (MsbuildItem c : msbuildConfiguration.getItems()) {
            if (!strings.contains(c.getFile())) {
                strings.add(c.getFile());
            }
        }
        List<File> files = new ArrayList<>();
        for (String s : strings) {
            files.add(new File(s));
        }
        return files;
    }

    @Input
    public Property<String> getVersion() {
        return version;
    }

    @Input
    public Property<Integer> getVersionCode() {
        return versionCode;
    }

    @Input
    public MsbuildConfiguration getMsbuildConfiguration() {
        return msbuildConfiguration;
    }

    public void setMsbuildConfiguration(MsbuildConfiguration msbuildConfiguration) {
        this.msbuildConfiguration = msbuildConfiguration;
    }

    @Input
    public Property<Boolean> getDisabled() {
        return disabled;
    }
}
